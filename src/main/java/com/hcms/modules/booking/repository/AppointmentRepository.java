package com.hcms.modules.booking.repository;

import com.hcms.modules.booking.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    
    /**
     * Finds all appointments for a specific doctor on a specific date.
     * Used to calculate available slots by excluding booked slots.
     */
    List<Appointment> findByDoctorIdAndAppointmentDateAndStatusNot(UUID doctorId, LocalDate appointmentDate, Appointment.AppointmentStatus status);

    /**
     * Checks if a doctor already has an active appointment at a specific slot.
     * Prevents double-booking (Business Rule BR-01).
     */
    boolean existsById(UUID id);

    boolean existsByDoctorIdAndAppointmentDateAndTimeSlotAndStatusNot(UUID doctorId, LocalDate appointmentDate, String timeSlot, Appointment.AppointmentStatus status);

    /**
     * Filter appointments for Dashboard (UC-05).
     * Supports optional status and doctorId filtering for the specific date.
     */
    @org.springframework.data.jpa.repository.Query("SELECT a FROM Appointment a " +
            "WHERE a.appointmentDate = :date " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:doctorId IS NULL OR a.doctor.id = :doctorId) " +
            "ORDER BY a.timeSlot ASC")
    List<Appointment> findForDashboard(
            @org.springframework.data.repository.query.Param("date") LocalDate date,
            @org.springframework.data.repository.query.Param("status") Appointment.AppointmentStatus status,
            @org.springframework.data.repository.query.Param("doctorId") UUID doctorId);

    /**
     * Find all appointments within a specific date range.
     */
    List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAscTimeSlotAsc(LocalDate startDate, LocalDate endDate);
}
