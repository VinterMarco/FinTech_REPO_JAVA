package com.example.demo.common.exception;

public class ErrorResponse {
    private final long timestamp;
    private final int httpStatus;
    private final String error;
    private final String message;
    private final String errorCode;
    private final int errorCodeValue;  // custom number e.g. 1001

    public ErrorResponse(long timestamp, int httpStatus, String error, String message, String errorCode, int errorCodeValue) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.error = error;
        this.message = message;
        this.errorCode = errorCode;
        this.errorCodeValue = errorCodeValue;
    }

    // getters
    public long getTimestamp() { return timestamp; }
    public int getHttpStatus() { return httpStatus; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getErrorCode() { return errorCode; }
    public int getErrorCodeValue() { return errorCodeValue; }
}