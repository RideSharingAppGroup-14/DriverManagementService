package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.driver.DriverDto;
import com.ridesharing.drivermanagementservice.dtos.requests.ProfileUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/profile")
public class ProfileController {

    @GetMapping("/{driver_id}")
    public ResponseEntity<?> getProfile(@PathVariable("driver_id") String driverId) {
        return ResponseEntity.ok(new DriverDto());
    }

    @PutMapping("/{driver_id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable("driver_id") String driverId,
            @RequestBody ProfileUpdateDto profileUpdateDto) {
        return ResponseEntity.ok(profileUpdateDto);
    }

}
