package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.requests.ProfileUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.UnableToProcessException;
import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.RideManagementClient;
import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos.RideStatusRequestDto;
import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos.RideStatusResponseDto;
import com.ridesharing.drivermanagementservice.externalclients.usermgmt.UserManagementClient;
import com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos.UpdateProfileRequestDto;
import com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos.UpdateProfileResponseDto;
import com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos.UserProfileDto;
import com.ridesharing.drivermanagementservice.models.DriverProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalServicesHandler {

    private final RideManagementClient rideManagementClient;
    private final UserManagementClient userManagementClient;

    @Value("${external.service.ride-mgmt.enabled}")
    private boolean rideManagementServiceEnabled;

    @Value("${external.service.user-mgmt.enabled}")
    private boolean userManagementServiceEnabled;

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

    public DriverProfile getProfile(String driverId) {
        if (userManagementServiceEnabled) {
            UserProfileDto userProfileDto = userManagementClient.getProfile(driverId);

            DriverProfile profile = new DriverProfile();
            profile.setDriverId(userProfileDto.getUserId());
            profile.setFirstName(userProfileDto.getFirstName());
            profile.setLastName(userProfileDto.getLastName());
            profile.setPhone(userProfileDto.getPhone());
            profile.setEmail(userProfileDto.getEmail());
            profile.setGender(userProfileDto.getGender());
            profile.setDob(userProfileDto.getDob());
            // Auto-approved
            profile.setApproved(true);
            profile.setCity(userProfileDto.getCity());
            profile.setState(userProfileDto.getState());
            profile.setCountry(userProfileDto.getCountry());
            return profile;
        }
        return null;
    }

    public void updateProfile(String driverId, ProfileUpdateDto profileUpdateDto) {
        if (userManagementServiceEnabled) {
            UpdateProfileRequestDto updateProfileRequestDto = new UpdateProfileRequestDto();
            updateProfileRequestDto.setPhone(profileUpdateDto.getPhone());
            updateProfileRequestDto.setEmail(profileUpdateDto.getEmail());
            updateProfileRequestDto.setCity(profileUpdateDto.getCity());
            updateProfileRequestDto.setState(profileUpdateDto.getState());
            updateProfileRequestDto.setCountry(profileUpdateDto.getCountry());

            UpdateProfileResponseDto updateProfileResponseDto = userManagementClient
                    .updateProfile(driverId, updateProfileRequestDto);
            if (updateProfileResponseDto == null) {
                throw new RuntimeException("Could not process this request");
            } else if (!updateProfileResponseDto.getSuccess()) {
                throw new UnableToProcessException(updateProfileResponseDto.getMessage());
            }
        }
    }
}
