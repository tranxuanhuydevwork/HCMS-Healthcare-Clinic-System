package com.hcms.modules.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlot {
    private LocalDate date;
    private String timeSlot; // e.g. "09:00"
}
