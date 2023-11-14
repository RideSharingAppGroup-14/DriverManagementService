package com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RideDetailsUpdateDto {
    private UUID rideId;
    private String status;
    private Float amount;
    private Double distance;
    private Integer duration;
    private DriverDetails driverDetails;
    private String cancellationReason;
}
