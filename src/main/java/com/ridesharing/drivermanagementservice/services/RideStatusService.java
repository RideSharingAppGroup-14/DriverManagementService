package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.CancelRideRequestDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidRideException;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;

public interface RideStatusService {
    ActiveRideDto getActiveRide(String driverId) throws NoActiveRideException;

    void startRide(String rideId, LocationDto locationDto)
            throws InvalidRideException, RideAlreadyProcessedException;

    void endRide(String rideId, LocationDto locationDto)
            throws InvalidRideException, RideAlreadyProcessedException;

    void cancelRide(String rideId, CancelRideRequestDto cancelRideRequestDto)
            throws InvalidRideException, RideAlreadyProcessedException;

    void notifyRideCompleted(String rideId) throws InvalidRideException;

    void notifyRideCancelled(String rideId) throws InvalidRideException;
}
