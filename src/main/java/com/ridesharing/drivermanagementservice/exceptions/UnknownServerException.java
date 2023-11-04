package com.ridesharing.drivermanagementservice.exceptions;

public class UnknownServerException extends RuntimeException {
    public UnknownServerException(String message) {
        super(message);
    }
}
