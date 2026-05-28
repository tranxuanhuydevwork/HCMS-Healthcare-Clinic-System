package com.hcms.common.exception;

import java.util.List;

/**
 * Structured response for validation errors.
 */
public record ValidationErrorResponse(List<FieldError> errors) {
    public record FieldError(String field, String message) {}
}
