package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidRideException;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;

public interface RideStatusService {
    ActiveRideDto getActiveRide(String driverId) throws NoActiveRideException;

    void startRide(String rideId, LocationDto locationDto) throws InvalidRideException, RideAlreadyProcessedException;
}
