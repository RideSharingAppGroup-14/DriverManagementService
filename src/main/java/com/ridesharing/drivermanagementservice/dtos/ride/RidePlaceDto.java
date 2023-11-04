package com.ridesharing.drivermanagementservice.dtos.ride;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ridesharing.drivermanagementservice.dtos.location.LocationDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RidePlaceDto extends LocationDto {
    private String name;
}
