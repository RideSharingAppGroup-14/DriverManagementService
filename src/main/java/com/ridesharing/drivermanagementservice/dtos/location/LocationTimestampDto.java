package com.ridesharing.drivermanagementservice.dtos.location;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class LocationTimestampDto extends LocationDto {
    private Instant timestamp;
}
