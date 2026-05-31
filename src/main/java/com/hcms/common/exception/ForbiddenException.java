package com.hcms.common.exception;

/**
 * Thrown on authorization failure (HTTP 403).
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
