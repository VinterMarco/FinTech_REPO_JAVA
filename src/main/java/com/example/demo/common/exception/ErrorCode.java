package com.example.demo.common.exception;

public enum ErrorCode {
    USER_NOT_FOUND(1001),
    PRODUCT_NOT_FOUND(1002),
    VALIDATION_FAILED(1003),
    INTERNAL_ERROR(1004),
    USER_INVALID(1005),
    DUPLICATE_RESOURCE(1006);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}