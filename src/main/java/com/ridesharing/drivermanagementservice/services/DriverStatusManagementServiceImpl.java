package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.AvailabilityStatusUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.MissingRequiredFieldsException;
import com.ridesharing.drivermanagementservice.models.Availability;
import com.ridesharing.drivermanagementservice.repositories.AvailabilityRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverStatusManagementServiceImpl implements DriverStatusManagementService {

    private final AvailabilityRepository availabilityRepository;

    public DriverStatusManagementServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

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
}
