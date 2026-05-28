package com.hcms.common.exception;

import lombok.Getter;

/**
 * Thrown when a business rule is violated (HTTP 422).
 */
@Getter
public class BusinessException extends RuntimeException {
    private final String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        this(message, "BUSINESS_ERROR");
    }
}
