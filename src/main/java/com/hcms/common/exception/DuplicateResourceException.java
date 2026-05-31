package com.hcms.common.exception;

/**
 * Thrown when an operation would violate a unique constraint (HTTP 409).
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
