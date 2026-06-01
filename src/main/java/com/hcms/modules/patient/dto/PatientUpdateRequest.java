package com.hcms.modules.patient.dto;

import com.hcms.modules.patient.entity.BloodType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateRequest {

    @DecimalMin(value = "0.0", message = "Height must be positive")
    @DecimalMax(value = "300.0", message = "Height exceeds logical limit")
    private BigDecimal heightCm;

    @DecimalMin(value = "0.0", message = "Weight must be positive")
    @DecimalMax(value = "300.0", message = "Weight exceeds logical limit")
    private BigDecimal weightKg;

    private BloodType bloodType;

    @Size(max = 500, message = "Allergies must not exceed 500 characters")
    private String allergies;

    @Size(max = 500, message = "Chronic conditions must not exceed 500 characters")
    private String chronicConditions;

    @Size(max = 500, message = "Vaccination notes must not exceed 500 characters")
    private String vaccinationNotes;
}
