package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.ride.RideHistoryDto;
import com.ridesharing.drivermanagementservice.exceptions.RidesNotFoundException;

public interface RideHistoryService {
    RideHistoryDto getRideHistory(String driverId, int offset, int limit) throws RidesNotFoundException;
}
