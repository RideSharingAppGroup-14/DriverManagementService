package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.ride.RideHistoryDto;
import com.ridesharing.drivermanagementservice.exceptions.RidesNotFoundException;

import java.util.UUID;

public interface RideHistoryService {
    RideHistoryDto getRideHistory(UUID driverId, int offset, int limit) throws RidesNotFoundException;
}
