package com.hcms.modules.clinical.controller;

import com.hcms.common.api.ApiResponse;
import com.hcms.modules.clinical.dto.VisitCreateRequest;
import com.hcms.modules.clinical.dto.VisitResponse;
import com.hcms.modules.clinical.service.ClinicalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/clinical")
@RequiredArgsConstructor
@Tag(name = "Clinical", description = "Doctor's EMR and Consultation API")
public class ClinicalController {

    private final ClinicalService clinicalService;

    @Operation(summary = "Record Clinical Consultation (UC-12)")
    @PostMapping("/visits")
    public ResponseEntity<ApiResponse<VisitResponse>> recordConsultation(@Valid @RequestBody VisitCreateRequest request) {
        VisitResponse response = clinicalService.recordConsultation(request);
        return ResponseEntity.ok(ApiResponse.created(response));
    }

    @Operation(summary = "Access Patient Medical History (UC-11)")
    @GetMapping("/patients/{patientId}/history")
    public ResponseEntity<ApiResponse<List<VisitResponse>>> getPatientHistory(@PathVariable UUID patientId) {
        List<VisitResponse> history = clinicalService.getPatientHistory(patientId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @Operation(summary = "Get Visit Details")
    @GetMapping("/visits/{visitId}")
    public ResponseEntity<ApiResponse<VisitResponse>> getVisitDetails(@PathVariable UUID visitId) {
        VisitResponse response = clinicalService.getVisitDetails(visitId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Finalize Consultation (UC-13 transition)")
    @PatchMapping("/visits/{visitId}/finalize")
    public ResponseEntity<ApiResponse<Void>> finalizeVisit(@PathVariable UUID visitId) {
        clinicalService.finalizeVisit(visitId);
        return ResponseEntity.ok(ApiResponse.success("Visit finalized", null));
    }
}
