package com.example.demo.common.exception;

public class InvalidCredentialsException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidCredentialsException(String message) {
        super(message);
        this.errorCode = ErrorCode.USER_INVALID;
    }

    public InvalidCredentialsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}