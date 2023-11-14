package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.CancelRideRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideLocationUpdateDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidRideException;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;

import java.util.UUID;

public interface RideStatusService {
    ActiveRideDto getActiveRide(UUID driverId) throws NoActiveRideException;

    void startRide(UUID rideId, LocationDto locationDto)
            throws InvalidRideException, RideAlreadyProcessedException;

    void endRide(UUID rideId, LocationDto locationDto)
            throws InvalidRideException, RideAlreadyProcessedException;

    void cancelRide(UUID rideId, CancelRideRequestDto cancelRideRequestDto)
            throws InvalidRideException, RideAlreadyProcessedException;

    void notifyRideCompleted(UUID rideId) throws InvalidRideException;

    void notifyRideCancelled(UUID rideId) throws InvalidRideException;

    void updateRideLocation(UUID rideId, RideLocationUpdateDto rideLocationUpdateDto)
            throws InvalidRideException;

}
