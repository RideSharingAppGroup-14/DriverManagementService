package com.ridesharing.drivermanagementservice.exceptions;

import com.ridesharing.drivermanagementservice.dtos.error.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EarningsNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEarningsNotFound(EarningsNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage()),
                httpStatus(HttpStatus.NOT_FOUND));
    }

    private HttpStatusCode httpStatus(HttpStatus httpStatus) {
        return HttpStatusCode.valueOf(httpStatus.value());
    }
}
