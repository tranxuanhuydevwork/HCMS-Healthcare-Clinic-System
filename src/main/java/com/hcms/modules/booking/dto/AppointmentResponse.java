package com.hcms.modules.booking.dto;

import com.hcms.modules.booking.entity.Appointment.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private UUID id;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
    private LocalDate appointmentDate;
    private String timeSlot;
    private AppointmentStatus status;
    private String reasonForVisit;
    private LocalDateTime createdAt;
}
