package com.hcms.modules.booking.entity;

import com.hcms.common.entity.BaseEntity;
import com.hcms.modules.doctor.entity.Doctor;
import com.hcms.modules.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a scheduled medical appointment.
 * ALL entities must extend BaseEntity.
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "appointment_date", nullable = false)
    private java.time.LocalDate appointmentDate;

    @Column(name = "time_slot", nullable = false, length = 100)
    private String timeSlot;

    @Column(name = "cancellation_reason", length = 255)
    private String cancellationReason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    public enum AppointmentStatus {
        PENDING, CONFIRMED, CANCELLED, CHECKED_IN, IN_PROGRESS, COMPLETED
    }
}
