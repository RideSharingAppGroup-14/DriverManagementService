package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.location.LocationTimestampDto;
import com.ridesharing.drivermanagementservice.dtos.requests.AvailabilityStatusUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.LocationNotFoundException;

public interface DriverStatusManagementService {

    void updateAvailability(String driverId, AvailabilityStatusUpdateDto availabilityStatusUpdateDto);

    void updateLocation(String driverId, LocationDto locationDto);

    LocationTimestampDto getLocation(String driverId) throws LocationNotFoundException;
}
