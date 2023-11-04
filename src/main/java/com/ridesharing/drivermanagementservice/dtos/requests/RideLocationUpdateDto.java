package com.ridesharing.drivermanagementservice.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ridesharing.drivermanagementservice.dtos.location.LocationTimestampDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RideLocationUpdateDto {

    private List<LocationTimestampDto> location;
}
