package com.hcms.common.api;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Standardized wrapper for paginated API results.
 * Wraps Spring Data JPA's Page object into a flat, immutable record.
 * 
 * @param content       The list of data for the current page.
 * @param page          The current page index (0-based).
 * @param size          The number of records per page.
 * @param totalElements Total records matching the query in the database.
 * @param totalPages    Total pages available.
 * @param last          True if this is the last page.
 * @param <T>           The type of the content list.
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
    /**
     * Factory method to convert a Spring Data Page into a PageResponse.
     */
    public static <T> PageResponse<T> from(Page<T> springPage) {
        return new PageResponse<>(
                springPage.getContent(),
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements(),
                springPage.getTotalPages(),
                springPage.isLast()
        );
    }
}
