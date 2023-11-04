package com.ridesharing.drivermanagementservice.dtos.ride;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RideHistoryDto {

    private RideHistoryMetadata metadata;

    private List<RideDto> rides;
}
