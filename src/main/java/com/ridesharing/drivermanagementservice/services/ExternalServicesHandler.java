package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.exceptions.UnableToProcessException;
import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.RideManagementClient;
import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos.RideStatusRequestDto;
import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos.RideStatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalServicesHandler {

    private final RideManagementClient rideManagementClient;

    @Value("${external.service.ride-mgmt.enabled}")
    private boolean rideManagementServiceEnabled;

    public void updateRideStatus(String rideId, String status) {
        if (rideManagementServiceEnabled) {
            // Update Ride status as assigned by notifying Ride Management Service
            RideStatusRequestDto rideStatusRequest = new RideStatusRequestDto();
            rideStatusRequest.setRideId(rideId);
            rideStatusRequest.setStatus(status);

            RideStatusResponseDto rideStatusResponse = rideManagementClient.updateRideStatus(rideStatusRequest);
            if (rideStatusResponse == null) {
                throw new RuntimeException("Could not process this request");
            } else if (!rideStatusResponse.getSuccess()) {
                throw new UnableToProcessException(rideStatusResponse.getMessage());
            }
        }
    }
}
