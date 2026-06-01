package com.hcms.modules.booking.controller;

import com.hcms.common.api.ApiResponse;
import com.hcms.modules.booking.dto.AppointmentResponse;
import com.hcms.modules.booking.dto.AppointmentStatusUpdateRequest;
import com.hcms.modules.booking.dto.BookAppointmentRequest;
import com.hcms.modules.booking.dto.WalkInAppointmentRequest;
import com.hcms.modules.booking.entity.Appointment.AppointmentStatus;
import com.hcms.modules.booking.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Appointment Management API")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Book Appointment Online (Self-Service)")
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> book(@Valid @RequestBody BookAppointmentRequest request) {
        log.info("REST request to book appointment for patient: {}", request.getPatient().getFullName());
        AppointmentResponse response = appointmentService.bookAppointment(request);
        return ResponseEntity.ok(ApiResponse.created(response));
    }

    @Operation(summary = "Register Walk-in Appointment")
    @PostMapping("/walk-in")
    public ResponseEntity<ApiResponse<AppointmentResponse>> registerWalkIn(@Valid @RequestBody WalkInAppointmentRequest request) {
        log.info("REST request to register walk-in for patient: {}", request.getPatient().getFullName());
        AppointmentResponse response = appointmentService.registerWalkIn(request);
        return ResponseEntity.ok(ApiResponse.created(response));
    }

    @Operation(summary = "View Today's Appointment Dashboard")
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getToday(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) UUID doctorId,
            @RequestParam(required = false) AppointmentStatus status) {
        
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        log.info("REST request to get dashboard for date: {}", targetDate);
        List<AppointmentResponse> response = appointmentService.getTodayAppointments(targetDate, doctorId, status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Update Appointment Status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody AppointmentStatusUpdateRequest request) {
        log.info("REST request to update appointment: {} to status: {}", id, request.getStatus());
        AppointmentResponse response = appointmentService.updateAppointmentStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Get All Appointments in Date Range")
    @GetMapping("/range")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("REST request to get appointments between {} and {}", startDate, endDate);
        List<AppointmentResponse> response = appointmentService.getAppointmentsInRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
