package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.constants.RideStatus;
import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.location.LocationTimestampDto;
import com.ridesharing.drivermanagementservice.dtos.requests.CancelRideRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideLocationUpdateDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidRideException;
import com.ridesharing.drivermanagementservice.exceptions.MissingRequiredFieldsException;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;
import com.ridesharing.drivermanagementservice.models.DriverStatus;
import com.ridesharing.drivermanagementservice.models.Earnings;
import com.ridesharing.drivermanagementservice.models.Ride;
import com.ridesharing.drivermanagementservice.models.RideLocation;
import com.ridesharing.drivermanagementservice.repositories.DriverStatusRepository;
import com.ridesharing.drivermanagementservice.repositories.EarningsRepository;
import com.ridesharing.drivermanagementservice.repositories.RideLocationRepository;
import com.ridesharing.drivermanagementservice.repositories.RideRepository;
import com.ridesharing.drivermanagementservice.utils.CoordinatesUtils;
import com.ridesharing.drivermanagementservice.utils.RideUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RideStatusServiceImpl implements RideStatusService {

    private final RideRepository rideRepository;
    private final EarningsRepository earningsRepository;
    private final RideLocationRepository rideLocationRepository;
    private final DriverStatusRepository driverStatusRepository;
    private final ExternalServicesHandler externalServicesHandler;

    @Value("${price.per-km}")
    float pricePerKm;

    private static final Set<String> VALID_RIDE_CANCELLABLE_STATUS = Set.of(
        RideStatus.CREATED.getValue(),
        RideStatus.PROCESSING.getValue(),
        RideStatus.ASSIGNED.getValue()
    );

    @Override
    public ActiveRideDto getActiveRide(String driverId) throws NoActiveRideException {
        Ride ride = rideRepository.findActiveRideByDriverId(driverId)
                .orElseThrow(() -> new NoActiveRideException("No active ride"));

        ActiveRideDto dto = new ActiveRideDto();
        dto.setRideId(ride.getRideId());
        dto.setPickup(RideUtils.getPickupDto(ride));
        dto.setDropoff(RideUtils.getDropOffDto(ride));
        dto.setRider(RideUtils.getRiderDto(ride));

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

        // Updating Ride using Ride Management Service
        externalServicesHandler.updateRideStarted(rideId, RideStatus.STARTED.getValue());

        ride.setStatus(RideStatus.STARTED.getValue());
        ride.setPickupLatitude(locationDto.getLatitude());
        ride.setPickupLongitude(locationDto.getLongitude());
        ride.setPickupTimestamp(Instant.now());
        rideRepository.save(ride);

        // Update pickup coordinates as the first ride location
        updateRideLocation(locationDto, ride);

        // Update driver's availability to false and coordinates as the latest driver's location
        updateDriverStatus(ride, false, locationDto);
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

        // Update status, distance, amount, duration
        Instant currentTime = Instant.now();
        double distance = CoordinatesUtils.calculateDistance(
            ride.getPickupLatitude(), ride.getPickupLongitude(),
            locationDto.getLatitude(), locationDto.getLongitude()
        );
        float amount = (float) (distance * pricePerKm);
        int duration = (int) Duration.between(ride.getPickupTimestamp(), currentTime).toMinutes();

        // Updating Ride details using Ride Management Service
        externalServicesHandler.updateRideEnded(rideId, RideStatus.ENDED.getValue(), distance, amount, duration);

        ride.setDistance(distance);
        ride.setAmount(amount);
        ride.setDuration(duration);
        ride.setStatus(RideStatus.ENDED.getValue());
        ride.setDropoffLatitude(locationDto.getLatitude());
        ride.setDropoffLongitude(locationDto.getLongitude());
        ride.setDropoffTimestamp(Instant.now());
        rideRepository.save(ride);

        // Update dropoff coordinates as the last ride location
        updateRideLocation(locationDto, ride);

        // Update driver's availability to true and coordinates as the latest driver's location
        updateDriverStatus(ride, true, locationDto);
    }

    @Override
    public void cancelRide(String rideId, CancelRideRequestDto cancelRideRequestDto) throws InvalidRideException, RideAlreadyProcessedException {
        if (cancelRideRequestDto.getLatitude() == null || cancelRideRequestDto.getLongitude() == null) {
            throw new MissingRequiredFieldsException("Location details are missing");
        }

        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));

        if (VALID_RIDE_CANCELLABLE_STATUS.contains(ride.getStatus())) {
            // Updating Ride using Ride Management Service
            externalServicesHandler.updateRideCancelled(rideId, RideStatus.CANCELLED.getValue(), cancelRideRequestDto.getReason());

            ride.setStatus(RideStatus.CANCELLED.getValue());
            rideRepository.save(ride);

            updateDriverStatus(ride, true, cancelRideRequestDto);
        } else if (RideStatus.CANCELLED.getValue().equals(ride.getStatus())) {
            throw new RideAlreadyProcessedException("Ride has already been cancelled");
        } else {
            throw new RideAlreadyProcessedException("Ride cannot be cancelled");
        }
    }

    @Override
    public void notifyRideCompleted(String rideId) throws InvalidRideException {
        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));
        if (RideStatus.ENDED.getValue().equals(ride.getStatus())) {

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

    // This should be called when Rider cancels it
    @Override
    public void notifyRideCancelled(String rideId) throws InvalidRideException {
        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));

        if (VALID_RIDE_CANCELLABLE_STATUS.contains(ride.getStatus())) {
            ride.setStatus(RideStatus.CANCELLED.getValue());
            rideRepository.save(ride);

            // Update driver's availability to true
            updateDriverStatus(ride, true, null);
        } else if (!RideStatus.CANCELLED.getValue().equals(ride.getStatus())) {
            throw new InvalidRideException("Cannot cancel the ride");
        }
        // No action if already cancelled
    }

    @Override
    public void updateRideLocation(String rideId, RideLocationUpdateDto rideLocationUpdateDto) throws InvalidRideException {
        Ride ride = rideRepository.findByRideId(rideId)
                .orElseThrow(() -> new InvalidRideException("Invalid ride"));
        if (RideStatus.STARTED.getValue().equals(ride.getStatus())) {
            // Update ride location if status is started
            if (rideLocationUpdateDto.getLocation() != null) {
                RideLocation rideLocation;
                List<RideLocation> rideLocationList = new ArrayList<>();
                for (LocationTimestampDto location: rideLocationUpdateDto.getLocation()) {
                    rideLocation = new RideLocation();
                    rideLocation.setLatitude(location.getLatitude());
                    rideLocation.setLongitude(location.getLongitude());
                    rideLocation.setTimestamp(location.getTimestamp());
                    rideLocation.setRide(ride);

                    rideLocationList.add(rideLocation);
                }

                rideLocationRepository.saveAll(rideLocationList);
            }

        } else {
            throw new InvalidRideException("Ride is not active");
        }
    }

    private void updateRideLocation(LocationDto locationDto, Ride ride) {
        RideLocation rideLocation = new RideLocation();
        rideLocation.setLatitude(locationDto.getLatitude());
        rideLocation.setLongitude(locationDto.getLongitude());
        rideLocation.setTimestamp(Instant.now());
        rideLocation.setRide(ride);
        rideLocationRepository.save(rideLocation);
    }

    private void updateDriverStatus(Ride ride, boolean status, LocationDto locationDto) {
        Optional<DriverStatus> driverStatusOptional = driverStatusRepository.findByDriverId(ride.getDriverId());
        if (driverStatusOptional.isPresent()) {
            DriverStatus driverStatus = driverStatusOptional.get();
            driverStatus.setStatus(status);
            if (locationDto != null) {
                driverStatus.setLatitude(locationDto.getLatitude());
                driverStatus.setLongitude(locationDto.getLongitude());
            }
            driverStatusRepository.save(driverStatus);
        }
    }
}
