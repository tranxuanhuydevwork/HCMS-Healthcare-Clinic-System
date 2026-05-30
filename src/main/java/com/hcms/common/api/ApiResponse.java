package com.hcms.common.api;

/**
 * Universal API response wrapper for the HCMS project.
 * Uses Java records for immutability and conciseness, replacing the previous class-based implementation.
 * 
 * Usage Examples:
 * <pre>
 * return ApiResponse.success(patientDto);
 * return ApiResponse.error(404, "Patient not found");
 * </pre>
 * 
 * @param code    The HTTP Status (int) OR Custom Business Code (String).
 * @param message The descriptive message for the client.
 * @param data    The payload (can be null for errors).
 * @param <T>     The type of the payload.
 */
public record ApiResponse<T>(Object code, String message, T data) {

    /**
     * Factory method for a successful 200 OK response.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Thành công", data);
    }

    /**
     * Factory method for a successful 200 OK response with a custom message.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * Factory method for a successful 201 Created response.
     */
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201, "Khởi tạo thành công", data);
    }

    /**
     * Factory method for a successful 204 No Content response.
     */
    public static <T> ApiResponse<T> noContent() {
        return new ApiResponse<>(204, "Thành công", null);
    }

    /**
     * Factory method for a general error response.
     */
    public static <T> ApiResponse<T> error(Object code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    /**
     * Factory method for an error response with metadata (e.g., validation results).
     */
    public static <T> ApiResponse<T> error(Object code, String message, T errorDetails) {
        return new ApiResponse<>(code, message, errorDetails);
    }
}
