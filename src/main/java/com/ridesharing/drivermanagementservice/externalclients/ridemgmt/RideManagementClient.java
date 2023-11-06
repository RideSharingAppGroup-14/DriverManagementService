package com.ridesharing.drivermanagementservice.externalclients.ridemgmt;

import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos.RideStatusRequestDto;
import com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos.RideStatusResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RideManagementClient {

    private final RestTemplate restTemplate;

    @Value("${ride-mgmt.status.url}")
    private String rideStatusUrl;

    public RideManagementClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RideStatusResponseDto updateRideStatus(RideStatusRequestDto request) {
        ResponseEntity<RideStatusResponseDto> responseEntity =
                restTemplate.exchange(rideStatusUrl, HttpMethod.PUT, new HttpEntity<>(request), RideStatusResponseDto.class);
        RideStatusResponseDto response = responseEntity.getBody();
        if (response != null) {
            response.setSuccess(responseEntity.getStatusCode().is2xxSuccessful());
        }
        return response;
    }
}
