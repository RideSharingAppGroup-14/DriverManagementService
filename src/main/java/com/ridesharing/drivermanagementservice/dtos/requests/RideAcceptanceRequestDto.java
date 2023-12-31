package com.ridesharing.drivermanagementservice.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RideAcceptanceRequestDto {

    @JsonProperty("ride_id")
    private UUID rideId;

    private boolean accepted;
}
