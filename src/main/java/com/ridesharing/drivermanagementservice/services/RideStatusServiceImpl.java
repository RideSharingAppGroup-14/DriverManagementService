package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.constants.RideStatus;
import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RidePlaceDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RiderDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidRideException;
import com.ridesharing.drivermanagementservice.exceptions.MissingRequiredFieldsException;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;
import com.ridesharing.drivermanagementservice.models.Ride;
import com.ridesharing.drivermanagementservice.repositories.RideRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RideStatusServiceImpl implements RideStatusService {

    private final RideRepository rideRepository;

    public RideStatusServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public ActiveRideDto getActiveRide(String driverId) throws NoActiveRideException {
        Ride ride = rideRepository.findActiveRideByDriverId(driverId)
                .orElseThrow(() -> new NoActiveRideException("No active ride"));

        ActiveRideDto dto = new ActiveRideDto();
        dto.setRideId(ride.getRideId());

        RidePlaceDto pickup = new RidePlaceDto();
        pickup.setLatitude(ride.getPickupLatitude());
        pickup.setLongitude(ride.getPickupLongitude());
        pickup.setName(ride.getPickupAddress());
        dto.setPickup(pickup);

        RidePlaceDto dropoff = new RidePlaceDto();
        dropoff.setLatitude(ride.getDropoffLatitude());
        dropoff.setLongitude(ride.getDropoffLongitude());
        dropoff.setName(ride.getDropoffAddress());
        dto.setDropoff(dropoff);

        RiderDto riderDto = new RiderDto();
        riderDto.setFirstName(ride.getRiderFirstName());
        riderDto.setLastName(ride.getRiderLastName());
        riderDto.setPhone(ride.getRiderPhone());
        dto.setRider(riderDto);

        return dto;
    }

    @Override
    public void startRide(String rideId, LocationDto locationDto) throws InvalidRideException, RideAlreadyProcessedException {
        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));
        if (RideStatus.STARTED.getValue().equals(ride.getStatus())) {
            throw new RideAlreadyProcessedException("Ride already started");
        } else if (!RideStatus.ASSIGNED.getValue().equals(ride.getStatus())) {
            throw new InvalidRideException("Invalid ride");
        } else if (locationDto.getLatitude() == null || locationDto.getLongitude() == null) {
            throw new MissingRequiredFieldsException("Location details are missing");
        }

        ride.setStatus(RideStatus.STARTED.getValue());
        ride.setPickupLatitude(locationDto.getLatitude());
        ride.setPickupLongitude(locationDto.getLongitude());
        ride.setPickupTimestamp(Instant.now());
        rideRepository.save(ride);
    }

    @Override
    public void endRide(String rideId, LocationDto locationDto) throws InvalidRideException, RideAlreadyProcessedException {
        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));
        if (RideStatus.ENDED.getValue().equals(ride.getStatus())) {
            throw new RideAlreadyProcessedException("Ride already ended");
        } else if (!RideStatus.STARTED.getValue().equals(ride.getStatus())) {
            throw new InvalidRideException("Invalid ride");
        } else if (locationDto.getLatitude() == null || locationDto.getLongitude() == null) {
            throw new MissingRequiredFieldsException("Location details are missing");
        }

        ride.setStatus(RideStatus.ENDED.getValue());
        ride.setDropoffLatitude(locationDto.getLatitude());
        ride.setDropoffLongitude(locationDto.getLongitude());
        ride.setDropoffTimestamp(Instant.now());
        rideRepository.save(ride);
    }
}
