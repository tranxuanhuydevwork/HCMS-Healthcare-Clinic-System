package com.hcms.modules.patient.controller;

import com.hcms.common.api.ApiResponse;
import com.hcms.modules.patient.dto.PatientCreateRequest;
import com.hcms.modules.patient.dto.PatientResponse;
import com.hcms.modules.patient.dto.PatientUpdateRequest;
import com.hcms.modules.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Patient Profile Management API")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Create New Patient", description = "Allow Receptionist to create or while Parent register the appointment, create new patient ")
    @PostMapping("/create")
    // @PreAuthorize("hasRole('DOCTOR')") // TODO: Re-enable when Auth module is implemented
    public ResponseEntity<ApiResponse<PatientResponse>> createPatientProfile(
            @Valid @RequestBody PatientCreateRequest request) {
        PatientResponse createPatient = patientService.createPatient(request);
        return ResponseEntity.ok(ApiResponse.success(createPatient));
    }

    @Operation(summary = "Search Patient by Phone Number", description = "Allows Receptionist to search for an existing patient")
    @GetMapping("/search")
    // @PreAuthorize("hasAnyRole('RECEPTIONIST', 'DOCTOR', 'ADMIN')") // TODO: Re-enable when Auth module is implemented
    public ResponseEntity<ApiResponse<List<PatientResponse>>> searchPatientByPhone(
            @RequestParam("phone") String phone) {
        log.info("REST request to search patients by phone: {}", phone);
        List<PatientResponse> patients = patientService.searchPatientsByPhone(phone);
        return ResponseEntity.ok(ApiResponse.success(patients));
    }

    @Operation(summary = "Get Patient Profile", description = "Returns full patient profile for Doctor's EMR view")
    @GetMapping("/{patientId}")
//    @PreAuthorize("hasAnyRole('DOCTOR', 'RECEPTIONIST')") // TODO: Re-enable when Auth module is implemented
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(
            @PathVariable UUID patientId) {
        log.info("REST request to get patient by ID: {}", patientId);
        PatientResponse patient = patientService.getPatientById(patientId);
        return ResponseEntity.ok(ApiResponse.success(patient));
    }

    @Operation(summary = "Update Patient Medical Profile", description = "Allows Doctor to update supplementary medical information")
    @PatchMapping("/{patientId}")
    // @PreAuthorize("hasRole('DOCTOR')") // TODO: Re-enable when Auth module is implemented
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatientProfile(
            @PathVariable UUID patientId,
            @Valid @RequestBody PatientUpdateRequest request) {
        log.info("REST request to update patient profile: {}", patientId);
        PatientResponse updatedPatient = patientService.updatePatientProfile(patientId, request);
        return ResponseEntity.ok(ApiResponse.success(updatedPatient));
    }

    // Note: GET /api/v1/patients/{patientId}/medical-history will be implemented in the EMR/Visit vertical slice.


}
