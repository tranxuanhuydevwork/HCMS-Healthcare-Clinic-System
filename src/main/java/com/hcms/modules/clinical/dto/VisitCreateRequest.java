package com.hcms.modules.clinical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class VisitCreateRequest {
    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    private UUID appointmentId;

    @NotBlank(message = "Symptoms are required")
    private String symptoms;

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    private String clinicalNotes;
}
