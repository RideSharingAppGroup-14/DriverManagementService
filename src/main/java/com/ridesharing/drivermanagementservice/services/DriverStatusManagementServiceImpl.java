package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.AvailabilityStatusUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.MissingRequiredFieldsException;
import com.ridesharing.drivermanagementservice.models.Availability;
import com.ridesharing.drivermanagementservice.models.Location;
import com.ridesharing.drivermanagementservice.repositories.AvailabilityRepository;
import com.ridesharing.drivermanagementservice.repositories.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DriverStatusManagementServiceImpl implements DriverStatusManagementService {

    private AvailabilityRepository availabilityRepository;
    private LocationRepository locationRepository;

    @Override
    public void updateAvailability(String driverId, AvailabilityStatusUpdateDto availabilityStatusUpdateDto) {
        if (availabilityStatusUpdateDto.getStatus() == null) {
            throw new MissingRequiredFieldsException("Status field is missing");
        }

        Availability availability = availabilityRepository.findByDriverId(driverId)
                .orElse(new Availability());

        availability.setStatus(availabilityStatusUpdateDto.getStatus());
        availability.setDriverId(driverId);

        availabilityRepository.save(availability);
    }

    @Override
    public void updateLocation(String driverId, LocationDto locationDto) {
        if (locationDto.getLatitude() == null
                || locationDto.getLongitude() == null) {
            throw new MissingRequiredFieldsException("Latitude/Longitude fields are missing");
        }

        Location location = locationRepository.findByDriverId(driverId)
                .orElse(new Location());

        location.setDriverId(driverId);
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());

        locationRepository.save(location);
    }
}
