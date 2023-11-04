package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import com.ridesharing.drivermanagementservice.dtos.location.LocationTimestampDto;
import com.ridesharing.drivermanagementservice.dtos.requests.AvailabilityStatusUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1")
public class DriverStatusController {

    @PostMapping("/availability/{driver_id}")
    public ResponseEntity<?> updateAvailability(
            @PathVariable("driver_id") String driverId,
            @RequestBody AvailabilityStatusUpdateDto availabilityStatusUpdateDto) {
        return ResponseEntity.ok(availabilityStatusUpdateDto);
    }

    @PutMapping("/location/{driver_id}")
    public ResponseEntity<?> updateLocation(
            @PathVariable("driver_id") String driverId,
            @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationDto);
    }

    @GetMapping("/location/{driver_id}")
    public ResponseEntity<LocationTimestampDto> getLocation(@PathVariable("driver_id") String driverId) {
        LocationTimestampDto locationDto = new LocationTimestampDto();
        locationDto.setLatitude(3.45);
        locationDto.setLongitude(3.43);
        locationDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(locationDto);
    }
}
