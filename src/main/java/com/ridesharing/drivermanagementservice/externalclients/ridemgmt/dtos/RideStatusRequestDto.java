package com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RideStatusRequestDto {
    private String rideId;
    private String status;
}
