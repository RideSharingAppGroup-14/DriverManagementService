package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.requests.CancelRideRequestDto;
import com.ridesharing.drivermanagementservice.dtos.requests.RideLocationUpdateDto;
import com.ridesharing.drivermanagementservice.dtos.ride.ActiveRideDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ride/active")
public class RideStatusController {

    @GetMapping("/{driver_id}")
    public ResponseEntity<ActiveRideDto> getActiveRide(@PathVariable("driver_id") String driverId) {
        return ResponseEntity.ok(new ActiveRideDto());
    }

    @PutMapping("/start/{ride_id}")
    public ResponseEntity<?> startRide(
            @PathVariable("ride_id") String rideId,
            @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationDto);
    }

    @PutMapping("/end/{ride_id}")
    public ResponseEntity<?> endRide(
            @PathVariable("ride_id") String rideId,
            @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationDto);
    }

    @PutMapping("/cancel/{ride_id}")
    public ResponseEntity<?> cancelRide(
            @PathVariable("ride_id") String rideId,
            @RequestBody CancelRideRequestDto cancelRideRequestDto) {
        return ResponseEntity.ok(cancelRideRequestDto);
    }

    @PutMapping("/completed/{ride_id}")
    public ResponseEntity<?> notifyRideCompleted(
            @PathVariable("ride_id") String rideId) {
        return ResponseEntity.ok().build();
    }
    @PutMapping("/location/{ride_id}")
    public ResponseEntity<?> updateRideLocation(
            @PathVariable("ride_id") String rideId,
            @RequestBody RideLocationUpdateDto rideLocationUpdateDto) {
        return ResponseEntity.ok(rideLocationUpdateDto);
    }
}