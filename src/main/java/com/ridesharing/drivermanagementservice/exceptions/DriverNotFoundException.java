package com.ridesharing.drivermanagementservice.exceptions;

public class DriverNotFoundException extends Exception {
    public DriverNotFoundException(String message) {
        super(message);
    }
}
