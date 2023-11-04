package com.ridesharing.drivermanagementservice.exceptions;

public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException(String message) {
        super(message);
    }
}
