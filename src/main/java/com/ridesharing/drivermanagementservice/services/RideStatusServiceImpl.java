package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RidePlaceDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RiderDto;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.models.Ride;
import com.ridesharing.drivermanagementservice.repositories.RideRepository;
import org.springframework.stereotype.Service;

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
}
