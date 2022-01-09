package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    /**
     * The personal message when a exception start
     */
    private final String message;
    /**
     * The status of the request
     */
    private final HttpStatus httpStatus;
    /**
     * The time when the exception was threw
     */
    private final ZonedDateTime timestamp;

    /**
     * Constructor class
     * @param message
     * @param httpStatus
     * @param timestamp
     */
    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
