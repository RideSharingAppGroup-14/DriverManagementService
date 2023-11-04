package com.ridesharing.drivermanagementservice.exceptions;

public class RideAlreadyProcessedException extends Exception {
    public RideAlreadyProcessedException(String message) {
        super(message);
    }
}
