package com.example.demo.common.exception;

public class DuplicateResourceException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateResourceException(String message) {
        super(message);
        this.errorCode = ErrorCode.USER_INVALID;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}