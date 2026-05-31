package com.hcms.modules.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String specialty;
    private String licenseNumber;
    private String biography;
}
