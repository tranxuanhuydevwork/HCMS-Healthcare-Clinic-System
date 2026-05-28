package com.hcms.modules.patient.dto;

import com.hcms.modules.patient.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotNull(message = "Date of birth is required")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Parent's phone number is required")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters")
    private String parentPhoneNumber;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String parentEmail;

    @Size(max = 500, message = "Reason for visit must not exceed 500 characters")
    private String reasonForVisit;
    
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;
}
