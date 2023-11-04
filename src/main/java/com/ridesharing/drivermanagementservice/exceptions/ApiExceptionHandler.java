package com.ridesharing.drivermanagementservice.exceptions;

import com.ridesharing.drivermanagementservice.dtos.error.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EarningsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEarningsNotFound(EarningsNotFoundException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(MissingRequiredFieldsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMissingFieldsException(MissingRequiredFieldsException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(UnknownServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleUnknownServerException(UnknownServerException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }
}
