package com.example.demo.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String resourceName;
    private final Object resourceId;

    public ResourceNotFoundException(ErrorCode errorCode, String resourceName, Object resourceId) {
        super(resourceName + " not found with id: " + resourceId);
        this.errorCode = errorCode;
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Object getResourceId() {
        return resourceId;
    }
}