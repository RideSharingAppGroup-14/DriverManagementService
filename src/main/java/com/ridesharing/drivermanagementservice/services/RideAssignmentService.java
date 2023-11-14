package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.requests.RideAcceptanceRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideRequestDto;
import com.ridesharing.drivermanagementservice.exceptions.DriverNotFoundException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;
import com.ridesharing.drivermanagementservice.exceptions.ServiceNotAvailableException;

import java.util.UUID;

public interface RideAssignmentService {
    void rideRequestNotification(RideRequestDto rideRequestDto)
            throws ServiceNotAvailableException, DriverNotFoundException, RideAlreadyProcessedException;

    void rideAcceptance(UUID driverId, RideAcceptanceRequestDto rideAcceptanceRequestDto)
            throws RideAlreadyProcessedException;
}
