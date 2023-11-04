package com.ridesharing.drivermanagementservice.constants;

import lombok.Getter;

@Getter
public enum RideStatus {
    CREATED("created"),
    PROCESSING("processing"),
    ASSIGNED("assigned"),
    STARTED("started"),
    ENDED("ended"),
    COMPLETED("completed"),
    CANCELLED("cancelled"),
    EXPIRED("expired");

    private final String value;

    RideStatus(String value) {
        this.value = value;
    }
}
