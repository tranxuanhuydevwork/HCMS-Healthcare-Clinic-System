package com.hcms.modules.booking.dto;

import com.hcms.modules.patient.dto.PatientCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalkInAppointmentRequest {

    @NotNull(message = "Doctor ID is required")
    private UUID doctorId;

    private String timeSlot; // Optional for walk-in, can be calculated current time

    @Valid
    @NotNull(message = "Patient information is required")
    private PatientCreateRequest patient;
}
