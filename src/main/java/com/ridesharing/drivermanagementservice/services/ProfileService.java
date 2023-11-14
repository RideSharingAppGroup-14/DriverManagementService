package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.driver.DriverDto;
import com.ridesharing.drivermanagementservice.dtos.requests.ProfileUpdateDto;

import java.util.UUID;

public interface ProfileService {
    DriverDto getProfile(UUID driverId);

    void updateProfile(UUID driverId, ProfileUpdateDto profileUpdateDto);
}
