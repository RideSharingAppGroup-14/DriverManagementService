package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.location.LocationTimestampDto;
import com.ridesharing.drivermanagementservice.dtos.requests.AvailabilityStatusUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.LocationNotFoundException;
import com.ridesharing.drivermanagementservice.exceptions.MissingRequiredFieldsException;
import com.ridesharing.drivermanagementservice.models.City;
import com.ridesharing.drivermanagementservice.models.DriverStatus;
import com.ridesharing.drivermanagementservice.repositories.CityRepository;
import com.ridesharing.drivermanagementservice.repositories.DriverStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverStatusManagementServiceImpl implements DriverStatusManagementService {

    private DriverStatusRepository driverStatusRepository;
    private CityRepository cityRepository;

    @Override
    public void updateAvailability(String driverId, AvailabilityStatusUpdateDto availabilityStatusUpdateDto) {
        if (availabilityStatusUpdateDto.getStatus() == null) {
            throw new MissingRequiredFieldsException("Status field is missing");
        }

        DriverStatus driverStatus = driverStatusRepository.findByDriverId(driverId)
                .orElse(new DriverStatus());

        Instant currentTime = Instant.now();
        if (availabilityStatusUpdateDto.getStatus()) {
            Double latitude = availabilityStatusUpdateDto.getLatitude();
            Double longitude = availabilityStatusUpdateDto.getLongitude();
            if (latitude == null || longitude == null) {
                throw new MissingRequiredFieldsException("Latitude/Longitude are missing");
            }
            driverStatus.setLatitude(latitude);
            driverStatus.setLongitude(longitude);
            driverStatus.setLocationUpdatedAt(currentTime);

            // Get city from coordinates
            Optional<City> cityOptional = cityRepository.findByCoordinates(latitude, longitude);
            cityOptional.ifPresent(driverStatus::setCity);
        }
        driverStatus.setStatus(availabilityStatusUpdateDto.getStatus());
        driverStatus.setStatusUpdatedAt(currentTime);
        driverStatus.setDriverId(driverId);

        driverStatusRepository.save(driverStatus);
    }

    @Override
    public void updateLocation(String driverId, LocationDto locationDto) {
        if (locationDto.getLatitude() == null
                || locationDto.getLongitude() == null) {
            throw new MissingRequiredFieldsException("Latitude/Longitude fields are missing");
        }

        DriverStatus driverStatus = driverStatusRepository.findByDriverId(driverId)
                .orElse(new DriverStatus());

        driverStatus.setDriverId(driverId);
        driverStatus.setLatitude(locationDto.getLatitude());
        driverStatus.setLongitude(locationDto.getLongitude());

        driverStatusRepository.save(driverStatus);
    }

    @Override
    public LocationTimestampDto getLocation(String driverId) throws LocationNotFoundException {
        DriverStatus driverStatus = driverStatusRepository.findByDriverId(driverId)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));

        if (driverStatus.getLatitude() == null || driverStatus.getLongitude() == null) {
            throw new LocationNotFoundException("Location not found");
        }

        LocationTimestampDto dto = new LocationTimestampDto();
        dto.setLatitude(driverStatus.getLatitude());
        dto.setLongitude(driverStatus.getLongitude());
        dto.setTimestamp(driverStatus.getLocationUpdatedAt());

        return dto;
    }
}
