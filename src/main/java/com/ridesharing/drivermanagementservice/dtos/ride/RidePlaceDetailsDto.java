package com.ridesharing.drivermanagementservice.dtos.ride;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ridesharing.drivermanagementservice.dtos.location.LocationTimestampDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RidePlaceDetailsDto extends LocationTimestampDto {
    private String name;
}
