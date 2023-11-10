package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.constants.RideStatus;
import com.ridesharing.drivermanagementservice.dtos.requests.RideAcceptanceRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RidePlaceRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideRequestDto;
import com.ridesharing.drivermanagementservice.exceptions.DriverNotFoundException;
import com.ridesharing.drivermanagementservice.exceptions.InvalidDriverException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;
import com.ridesharing.drivermanagementservice.exceptions.ServiceNotAvailableException;
import com.ridesharing.drivermanagementservice.models.City;
import com.ridesharing.drivermanagementservice.models.DriverProfile;
import com.ridesharing.drivermanagementservice.models.DriverStatus;
import com.ridesharing.drivermanagementservice.models.Ride;
import com.ridesharing.drivermanagementservice.repositories.CityRepository;
import com.ridesharing.drivermanagementservice.repositories.DriverProfileRepository;
import com.ridesharing.drivermanagementservice.repositories.DriverStatusRepository;
import com.ridesharing.drivermanagementservice.repositories.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideAssignmentServiceImpl implements RideAssignmentService {

    private final RideRepository rideRepository;
    private final CityRepository cityRepository;
    private final DriversSearchStrategy driversSearchStrategy;
    private final DriverStatusRepository driverStatusRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final ExternalServicesHandler externalServicesHandler;

    @Value("${search.radius.in-km}")
    private Integer searchRadiusInKm;

    @Value("${ride.request.expiry.in-minutes}")
    private Integer rideRequestExpiryInMinutes;

    @Override
    public void rideRequestNotification(RideRequestDto rideRequestDto)
            throws ServiceNotAvailableException, DriverNotFoundException, RideAlreadyProcessedException {
        // Validate pickup location
        RidePlaceRequestDto pickup = rideRequestDto.getPickup();
        Optional<City> cityOptional = cityRepository.findByCoordinates(
                pickup.getLatitude(), pickup.getLongitude());
        if (cityOptional.isEmpty()) {
            throw new ServiceNotAvailableException("Service not available in this area");
        }

        // Validate dropoff location
        Optional<City> dropoffCityOptional = cityRepository.findByCoordinates(
                rideRequestDto.getDropoff().getLatitude(), rideRequestDto.getDropoff().getLongitude());
        if (dropoffCityOptional.isEmpty()) {
            throw new ServiceNotAvailableException("Service not available in this area");
        }

        // Search for drivers in the resulted city with a radius
        List<DriverStatus> driverStatusList = driversSearchStrategy.getNearbyDrivers(
                cityOptional.get().getId(), pickup.getLatitude(), pickup.getLongitude(), searchRadiusInKm
        );

        if (driverStatusList == null || driverStatusList.isEmpty()) {
            throw new DriverNotFoundException("No drivers available");
        }

        // Checking ride exists or not in a case for Retry logic
        Ride ride = rideRepository.findByRideId(rideRequestDto.getRideId())
                .orElse(new Ride());
        if (ride.getId() == null) {
            ride.setRideId(rideRequestDto.getRideId());
            ride.setStatus(RideStatus.PROCESSING.getValue());

            ride.setPickupAddress(rideRequestDto.getPickup().getName());
            ride.setPickupLatitude(rideRequestDto.getPickup().getLatitude());
            ride.setPickupLongitude(rideRequestDto.getPickup().getLongitude());

            ride.setDropoffAddress(rideRequestDto.getDropoff().getName());
            ride.setDropoffLatitude(rideRequestDto.getDropoff().getLatitude());
            ride.setDropoffLongitude(rideRequestDto.getDropoff().getLongitude());

            ride.setRiderFirstName(rideRequestDto.getRider().getFirstName());
            ride.setRiderLastName(rideRequestDto.getRider().getLastName());
            ride.setRiderPhone(rideRequestDto.getRider().getPhone());

            rideRepository.save(ride);
        } else if (!RideStatus.CREATED.getValue().equals(ride.getStatus())
            && !RideStatus.PROCESSING.getValue().equals(ride.getStatus())
            && !RideStatus.EXPIRED.getValue().equals(ride.getStatus())) {
            throw new RideAlreadyProcessedException("This ride has already been processed");
        } else if (RideStatus.EXPIRED.getValue().equals(ride.getStatus())) {
            // Renew the request for Retry
            ride.setStatus(RideStatus.PROCESSING.getValue());
            ride.setUpdatedAt(Instant.now());
            rideRepository.save(ride);
        }

        // Notify Drivers
    }

    @Override
    public void rideAcceptance(String driverId, RideAcceptanceRequestDto rideAcceptanceRequestDto)
            throws RideAlreadyProcessedException {
        DriverProfile profile = driverProfileRepository.findByDriverId(driverId)
                .orElseThrow(() -> new InvalidDriverException("Invalid driver"));
        Ride ride = rideRepository.findByRideId(rideAcceptanceRequestDto.getRideId())
                .orElse(new Ride());
        if (RideStatus.EXPIRED.getValue().equals(ride.getStatus())) {
            throw new RideAlreadyProcessedException("This request has expired");
        } else if (!RideStatus.CREATED.getValue().equals(ride.getStatus())
            && !RideStatus.PROCESSING.getValue().equals(ride.getStatus())) {
            throw new RideAlreadyProcessedException("This ride has already been processed");
        } else {
            long durationInMinutes = Duration.between(ride.getUpdatedAt(), Instant.now()).toMinutes();
            if (durationInMinutes > rideRequestExpiryInMinutes) {
                ride.setStatus(RideStatus.EXPIRED.getValue());
                rideRepository.save(ride);
                throw new RideAlreadyProcessedException("This request has expired");
            }
            if (rideAcceptanceRequestDto.isAccepted()) {
                // Pass driver details to Ride Management service while notifying
                // Updating Ride using Ride Management Service
                externalServicesHandler.updateRideAssigned(
                        rideAcceptanceRequestDto.getRideId(), RideStatus.ASSIGNED.getValue(), profile);

                ride.setDriverId(driverId);
                ride.setStatus(RideStatus.ASSIGNED.getValue());
                rideRepository.save(ride);

                // Update driver's availability to false and coordinates as the latest driver's location
                Optional<DriverStatus> driverStatusOptional = driverStatusRepository.findByDriverId(ride.getDriverId());
                if (driverStatusOptional.isPresent()) {
                    DriverStatus driverStatus = driverStatusOptional.get();
                    driverStatus.setStatus(false);
                    driverStatusRepository.save(driverStatus);
                }
            }
        }
    }
}
