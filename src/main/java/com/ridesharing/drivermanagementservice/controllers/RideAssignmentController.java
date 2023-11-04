package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.requests.RideAcceptanceRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideRequestDto;
import com.ridesharing.drivermanagementservice.exceptions.DriverNotFoundException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;
import com.ridesharing.drivermanagementservice.exceptions.ServiceNotAvailableException;
import com.ridesharing.drivermanagementservice.services.RideAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ride")
public class RideAssignmentController {

    private final RideAssignmentService rideAssignmentService;

    public RideAssignmentController(RideAssignmentService rideAssignmentService) {
        this.rideAssignmentService = rideAssignmentService;
    }

    @PostMapping("/assignment")
    public ResponseEntity<?> rideRequestNotification(@RequestBody RideRequestDto rideRequestDto)
            throws ServiceNotAvailableException, DriverNotFoundException, RideAlreadyProcessedException {
        rideAssignmentService.rideRequestNotification(rideRequestDto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/acceptance/{driver_id}")
    public ResponseEntity<?> rideAcceptance(
            @PathVariable("driver_id") String driverId,
            @RequestBody RideAcceptanceRequestDto rideAcceptanceRequestDto) throws RideAlreadyProcessedException {
        rideAssignmentService.rideAcceptance(driverId, rideAcceptanceRequestDto);
        return ResponseEntity.ok().build();
    }
}
