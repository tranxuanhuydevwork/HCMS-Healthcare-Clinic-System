package com.hcms.modules.booking.service;

import com.hcms.modules.booking.dto.AppointmentResponse;
import com.hcms.modules.booking.dto.AppointmentStatusUpdateRequest;
import com.hcms.modules.booking.dto.BookAppointmentRequest;
import com.hcms.modules.booking.dto.WalkInAppointmentRequest;
import com.hcms.modules.booking.entity.Appointment.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentResponse bookAppointment(BookAppointmentRequest request);

    AppointmentResponse registerWalkIn(WalkInAppointmentRequest request);

    List<AppointmentResponse> getTodayAppointments(LocalDate date, UUID doctorId, AppointmentStatus status);

    AppointmentResponse updateAppointmentStatus(UUID appointmentId, AppointmentStatusUpdateRequest request);
    
    List<AppointmentResponse> getAppointmentsInRange(LocalDate startDate, LocalDate endDate);
}
