package com.ridesharing.drivermanagementservice.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ridesharing.drivermanagementservice.dtos.ride.RiderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RideRequestDto {

    @JsonProperty("ride_id")
    private UUID rideId;

    private RidePlaceRequestDto pickup;
    private RidePlaceRequestDto dropoff;
    private RiderDto rider;

    private String status;
}
