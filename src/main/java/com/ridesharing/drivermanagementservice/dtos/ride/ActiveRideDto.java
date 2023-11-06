package com.ridesharing.drivermanagementservice.dtos.ride;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActiveRideDto {

    @JsonProperty("ride_id")
    private String rideId;

    private RidePlaceDetailsDto pickup;
    private RidePlaceDetailsDto dropoff;
    private RiderDto rider;
}
