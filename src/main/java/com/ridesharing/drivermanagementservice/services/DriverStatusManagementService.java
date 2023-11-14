package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.location.LocationTimestampDto;
import com.ridesharing.drivermanagementservice.dtos.requests.AvailabilityStatusUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.LocationNotFoundException;

import java.util.UUID;

public interface DriverStatusManagementService {

    void updateAvailability(UUID driverId, AvailabilityStatusUpdateDto availabilityStatusUpdateDto);

    void updateLocation(UUID driverId, LocationDto locationDto);

    LocationTimestampDto getLocation(UUID driverId) throws LocationNotFoundException;
}
