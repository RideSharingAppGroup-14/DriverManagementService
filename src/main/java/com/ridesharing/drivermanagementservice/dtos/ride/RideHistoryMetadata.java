package com.ridesharing.drivermanagementservice.dtos.ride;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RideHistoryMetadata {

    private int total;
    private int offset;
    private int limit;
    private int count;
}
