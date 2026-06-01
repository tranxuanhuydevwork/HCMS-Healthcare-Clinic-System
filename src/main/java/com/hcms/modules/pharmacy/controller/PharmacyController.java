package com.hcms.modules.pharmacy.controller;

import com.hcms.common.api.ApiResponse;
import com.hcms.modules.pharmacy.dto.PrescriptionRequest;
import com.hcms.modules.pharmacy.dto.PrescriptionResponse;
import com.hcms.modules.pharmacy.service.PharmacyService;
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
@RequestMapping("/pharmacy")
@RequiredArgsConstructor
@Tag(name = "Pharmacy", description = "Prescription Management API")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Operation(summary = "Issue Electronic Prescription (UC-13)")
    @PostMapping("/visits/{visitId}/prescriptions")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> issuePrescription(
            @PathVariable UUID visitId,
            @Valid @RequestBody List<PrescriptionRequest> requests) {
        List<PrescriptionResponse> response = pharmacyService.issuePrescription(visitId, requests);
        return ResponseEntity.ok(ApiResponse.created(response));
    }

    @Operation(summary = "View Clinical Notes & Prescriptions (UC-04)")
    @GetMapping("/visits/{visitId}/prescriptions")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptions(@PathVariable UUID visitId) {
        List<PrescriptionResponse> response = pharmacyService.getVisitPrescriptions(visitId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
