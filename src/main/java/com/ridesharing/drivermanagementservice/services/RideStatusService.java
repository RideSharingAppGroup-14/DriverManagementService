package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;

public interface RideStatusService {
    ActiveRideDto getActiveRide(String driverId) throws NoActiveRideException;
}
