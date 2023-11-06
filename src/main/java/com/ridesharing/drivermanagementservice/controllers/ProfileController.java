package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.driver.DriverDto;
import com.ridesharing.drivermanagementservice.dtos.requests.ProfileUpdateDto;
import com.ridesharing.drivermanagementservice.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{driver_id}")
    public ResponseEntity<?> getProfile(@PathVariable("driver_id") String driverId) {
        return ResponseEntity.ok(profileService.getProfile(driverId));
    }

    @PutMapping("/{driver_id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable("driver_id") String driverId,
            @RequestBody ProfileUpdateDto profileUpdateDto) {
        profileService.updateProfile(driverId, profileUpdateDto);
        return ResponseEntity.ok().build();
    }

}
