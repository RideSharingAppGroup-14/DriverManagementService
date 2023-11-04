package com.ridesharing.drivermanagementservice.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityStatusUpdateDto {
    private Boolean status;
}
