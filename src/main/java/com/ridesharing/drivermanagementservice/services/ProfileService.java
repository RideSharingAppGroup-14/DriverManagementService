package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.driver.DriverDto;
import com.ridesharing.drivermanagementservice.dtos.requests.ProfileUpdateDto;

public interface ProfileService {
    DriverDto getProfile(String driverId);

    void updateProfile(String driverId, ProfileUpdateDto profileUpdateDto);
}
