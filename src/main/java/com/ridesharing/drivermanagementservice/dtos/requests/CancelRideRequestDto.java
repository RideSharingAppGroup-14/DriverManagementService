package com.ridesharing.drivermanagementservice.dtos.requests;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelRideRequestDto extends LocationDto {

    private String reason;
}
