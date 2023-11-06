package com.ridesharing.drivermanagementservice.externalclients.usermgmt;

import com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos.UpdateProfileRequestDto;
import com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos.UpdateProfileResponseDto;
import com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos.UserProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserManagementClient {
    private final RestTemplate restTemplate;

    @Value("${user-mgmt.profile.url}")
    private String profileUrl;

    public UserManagementClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserProfileDto getProfile(String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        ResponseEntity<UserProfileDto> responseEntity = restTemplate.getForEntity(profileUrl, UserProfileDto.class, paramMap);
        return responseEntity.getBody();
    }

    public UpdateProfileResponseDto updateProfile(String userId, UpdateProfileRequestDto updateProfileRequestDto) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        ResponseEntity<UpdateProfileResponseDto> responseEntity =
                restTemplate.exchange(profileUrl, HttpMethod.PUT, new HttpEntity<>(updateProfileRequestDto),
                        UpdateProfileResponseDto.class, paramMap);
        UpdateProfileResponseDto response = responseEntity.getBody();
        if (response != null) {
            response.setSuccess(responseEntity.getStatusCode().is2xxSuccessful());
        }
        return response;
    }
}
