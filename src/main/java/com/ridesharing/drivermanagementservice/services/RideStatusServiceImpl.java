package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.constants.RideStatus;
import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.CancelRideRequestDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RidePlaceDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RiderDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidRideException;
import com.ridesharing.drivermanagementservice.exceptions.MissingRequiredFieldsException;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;
import com.ridesharing.drivermanagementservice.models.Earnings;
import com.ridesharing.drivermanagementservice.models.Ride;
import com.ridesharing.drivermanagementservice.repositories.EarningsRepository;
import com.ridesharing.drivermanagementservice.repositories.RideRepository;
import com.ridesharing.drivermanagementservice.utils.CoordinatesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RideStatusServiceImpl implements RideStatusService {

    private final RideRepository rideRepository;
    private final EarningsRepository earningsRepository;

    @Value("${price.per-km}")
    float pricePerKm;

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

        // TODO: Notify RideManagement Service

        // Update status, distance, amount, duration
        Instant currentTime = Instant.now();
        double distance = CoordinatesUtils.calculateDistance(
            ride.getPickupLatitude(), ride.getPickupLongitude(),
            locationDto.getLatitude(), locationDto.getLongitude()
        );
        float amount = (float) distance * pricePerKm;
        int duration = (int) Duration.between(ride.getPickupTimestamp(), currentTime).toMinutes();

        ride.setDistance(distance);
        ride.setAmount(amount);
        ride.setDuration(duration);
        ride.setStatus(RideStatus.ENDED.getValue());
        ride.setDropoffLatitude(locationDto.getLatitude());
        ride.setDropoffLongitude(locationDto.getLongitude());
        ride.setDropoffTimestamp(currentTime);
        rideRepository.save(ride);

    }

    @Override
    public void cancelRide(String rideId, CancelRideRequestDto cancelRideRequestDto) throws InvalidRideException, RideAlreadyProcessedException {
        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));
        Set<String> validStatus = Set.of(
                RideStatus.CREATED.getValue(),
                RideStatus.PROCESSING.getValue(),
                RideStatus.ASSIGNED.getValue());
        if (RideStatus.CANCELLED.getValue().equals(ride.getStatus())) {
            throw new RideAlreadyProcessedException("Ride has already been cancelled");
        } else if (!validStatus.contains(ride.getStatus())) {
            throw new RideAlreadyProcessedException("Ride cannot be cancelled");
        } else if (cancelRideRequestDto.getLatitude() == null || cancelRideRequestDto.getLongitude() == null) {
            throw new MissingRequiredFieldsException("Location details are missing");
        }

        // TODO: Notify RideManagement for cancellation from Driver's end
        ride.setStatus(RideStatus.CANCELLED.getValue());
        rideRepository.save(ride);
    }

    @Override
    public void notifyRideCompleted(String rideId) throws InvalidRideException {
        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));
        if (RideStatus.ENDED.getValue().equals(ride.getStatus())) {
            // TODO: Update ride status as completed in RideManagement Service

            // Update status as completed
            ride.setStatus(RideStatus.COMPLETED.getValue());
            rideRepository.save(ride);

            // Update earnings
            Earnings earnings = earningsRepository.findByDriverId(ride.getDriverId())
                    .orElse(new Earnings());
            earnings.setDriverId(ride.getDriverId());
            earnings.setTotalEarnings(earnings.getTotalEarnings() + ride.getAmount());
            earnings.setCurrentBalance(earnings.getCurrentBalance() + ride.getAmount());
            earningsRepository.save(earnings);
        } else if (!RideStatus.COMPLETED.getValue().equals(ride.getStatus())) {
            throw new InvalidRideException("Cannot mark this ride as completed");
        }
    }
}
