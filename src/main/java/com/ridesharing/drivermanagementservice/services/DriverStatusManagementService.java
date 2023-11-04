package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.AvailabilityStatusUpdateDto;

public interface DriverStatusManagementService {

    void updateAvailability(String driverId, AvailabilityStatusUpdateDto availabilityStatusUpdateDto);

    void updateLocation(String driverId, LocationDto locationDto);
}
