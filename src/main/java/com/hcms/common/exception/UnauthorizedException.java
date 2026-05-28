package com.hcms.common.exception;

/**
 * Thrown on authentication failure (HTTP 401).
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
