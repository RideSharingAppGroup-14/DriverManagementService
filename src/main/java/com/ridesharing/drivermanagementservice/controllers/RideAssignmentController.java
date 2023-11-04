package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.requests.RideAcceptanceRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ride")
public class RideAssignmentController {

    @PostMapping("/assignment")
    public ResponseEntity<?> rideRequestNotification(@RequestBody RideRequestDto rideRequestDto) {
        return new ResponseEntity<>(rideRequestDto, HttpStatus.ACCEPTED);
    }

    @PostMapping("/acceptance/{driver_id}")
    public ResponseEntity<?> rideAcceptance(
            @PathVariable("driver_id") String driverId,
            @RequestBody RideAcceptanceRequestDto rideAcceptanceRequestDto) {
        return ResponseEntity.ok(rideAcceptanceRequestDto);
    }
}
