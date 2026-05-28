package com.hcms.modules.patient.dto;

import com.hcms.modules.patient.entity.BloodType;
import com.hcms.modules.patient.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    
    private UUID id;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String parentPhoneNumber;
    private String parentEmail;
    private String address;

    private BigDecimal heightCm;
    private BigDecimal weightKg;
    private BloodType bloodType;
    private String allergies;
    private String chronicConditions;
    private String vaccinationNotes;

    private LocalDateTime createdAt;
}
