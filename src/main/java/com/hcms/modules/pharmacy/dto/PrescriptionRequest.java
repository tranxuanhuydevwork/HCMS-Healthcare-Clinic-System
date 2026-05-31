package com.hcms.modules.pharmacy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrescriptionRequest {
    @NotBlank(message = "Medicine name is required")
    private String medicineName;

    @NotBlank(message = "Dosage instruction is required")
    private String dosageInstruction;

    @NotBlank(message = "Duration is required")
    private String duration;
}
