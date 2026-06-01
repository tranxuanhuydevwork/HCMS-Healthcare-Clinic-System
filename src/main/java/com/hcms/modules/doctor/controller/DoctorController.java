package com.hcms.modules.doctor.controller;

import com.hcms.common.api.ApiResponse;
import com.hcms.modules.doctor.dto.DoctorResponse;
import com.hcms.modules.doctor.service.DoctorService;
import com.hcms.modules.booking.dto.AvailableSlot;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Public Doctor Listing and Scheduling API")
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "List all active Doctors")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> listDoctors() {
        log.info("REST request to list active doctors");
        List<DoctorResponse> doctors = doctorService.listActiveDoctors();
        return ResponseEntity.ok(ApiResponse.success(doctors));
    }

    @Operation(summary = "Get Available Time Slots for a Doctor")
    @GetMapping("/{doctorId}/available-slots")
    public ResponseEntity<ApiResponse<List<AvailableSlot>>> getDoctorAvailableSlots(
            @PathVariable UUID doctorId,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate) {
        
        LocalDate targetDate = (fromDate != null) ? fromDate : LocalDate.now();
        log.info("REST request to get available slots for doctor: {} on date: {}", doctorId, targetDate);
        
        List<AvailableSlot> slots = doctorService.getDoctorAvailableSlots(doctorId, targetDate);
        return ResponseEntity.ok(ApiResponse.success(slots));
    }
}
