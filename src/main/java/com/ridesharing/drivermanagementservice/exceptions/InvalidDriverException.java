package com.ridesharing.drivermanagementservice.exceptions;

public class InvalidDriverException extends RuntimeException {
    public InvalidDriverException(String message) {
        super(message);
    }
}
