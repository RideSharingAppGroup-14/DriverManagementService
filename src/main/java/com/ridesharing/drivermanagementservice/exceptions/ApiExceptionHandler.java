package com.ridesharing.drivermanagementservice.exceptions;

import com.ridesharing.drivermanagementservice.dtos.error.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
        EarningsNotFoundException.class,
        LocationNotFoundException.class,
        DriverNotFoundException.class,
        NoActiveRideException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFoundException(Exception ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponseDto handleServiceNotAvailable(ServiceNotAvailableException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler({
        MissingRequiredFieldsException.class,
        RideAlreadyProcessedException.class,
        InvalidRideException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleClientException(Exception ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleUnknownServerException() {
        return new ErrorResponseDto("An internal error occurred");
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body, HttpHeaders headers, HttpStatusCode statusCode,
                                                             WebRequest request) {
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode.value());
        return new ResponseEntity<>(new ErrorResponseDto("An internal error occurred."),
                httpStatus);
    }
}
