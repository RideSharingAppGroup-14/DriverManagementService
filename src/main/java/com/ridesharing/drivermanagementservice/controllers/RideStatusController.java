package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.CancelRideRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideLocationUpdateDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidRideException;
import com.ridesharing.drivermanagementservice.exceptions.NoActiveRideException;
import com.ridesharing.drivermanagementservice.exceptions.RideAlreadyProcessedException;
import com.ridesharing.drivermanagementservice.services.RideStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ride")
public class RideStatusController {

    private final RideStatusService rideStatusService;

    public RideStatusController(RideStatusService rideStatusService) {
        this.rideStatusService = rideStatusService;
    }

    @GetMapping("/active/{driver_id}")
    public ResponseEntity<ActiveRideDto> getActiveRide(@PathVariable("driver_id") String driverId) throws NoActiveRideException {
        return ResponseEntity.ok(rideStatusService.getActiveRide(driverId));
    }

    @PutMapping("/active/start/{ride_id}")
    public ResponseEntity<?> startRide(
            @PathVariable("ride_id") String rideId,
            @RequestBody LocationDto locationDto) throws RideAlreadyProcessedException, InvalidRideException {
        rideStatusService.startRide(rideId, locationDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/end/{ride_id}")
    public ResponseEntity<?> endRide(
            @PathVariable("ride_id") String rideId,
            @RequestBody LocationDto locationDto) throws RideAlreadyProcessedException, InvalidRideException {
        rideStatusService.endRide(rideId, locationDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/cancel/{ride_id}")
    public ResponseEntity<?> cancelRide(
            @PathVariable("ride_id") String rideId,
            @RequestBody CancelRideRequestDto cancelRideRequestDto) throws RideAlreadyProcessedException, InvalidRideException {
        rideStatusService.cancelRide(rideId, cancelRideRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/notify/completed/{ride_id}")
    public ResponseEntity<?> notifyRideCompleted(
            @PathVariable("ride_id") String rideId) throws InvalidRideException {
        rideStatusService.notifyRideCompleted(rideId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/notify/cancelled/{ride_id}")
    public ResponseEntity<?> notifyRideCancelled(
            @PathVariable("ride_id") String rideId) throws InvalidRideException {
        rideStatusService.notifyRideCancelled(rideId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/location/{ride_id}")
    public ResponseEntity<?> updateRideLocation(
            @PathVariable("ride_id") String rideId,
            @RequestBody RideLocationUpdateDto rideLocationUpdateDto) throws InvalidRideException {
        rideStatusService.updateRideLocation(rideId, rideLocationUpdateDto);
        return ResponseEntity.ok().build();
    }
}
