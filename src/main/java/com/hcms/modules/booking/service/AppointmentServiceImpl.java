package com.hcms.modules.booking.service;

import com.hcms.common.exception.BusinessException;
import com.hcms.common.exception.ResourceNotFoundException;
import com.hcms.modules.doctor.entity.Doctor;
import com.hcms.modules.doctor.repository.DoctorRepository;
import com.hcms.modules.booking.dto.AppointmentResponse;
import com.hcms.modules.booking.dto.AppointmentStatusUpdateRequest;
import com.hcms.modules.booking.dto.BookAppointmentRequest;
import com.hcms.modules.booking.dto.WalkInAppointmentRequest;
import com.hcms.modules.booking.entity.Appointment;
import com.hcms.modules.booking.mapper.AppointmentMapper;
import com.hcms.modules.booking.repository.AppointmentRepository;
import com.hcms.modules.patient.dto.PatientResponse;
import com.hcms.modules.patient.entity.Patient;
import com.hcms.modules.patient.repository.PatientRepository;
import com.hcms.modules.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;
    
    // Using PatientService logic for UC-02
    private final PatientService patientService;
    private final PatientRepository patientRepository; // Needed to fetch the managed entity

    @Override
    @Transactional
    public AppointmentResponse bookAppointment(BookAppointmentRequest request) {
        log.info("Processing booking request for doctor: {} at {} {}", 
                request.getDoctorId(), request.getAppointmentDate(), request.getTimeSlot());

        // 1. Verify Doctor exists
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", request.getDoctorId().toString()));

        // 2. Business Rule BR-01: Check Slot Availability
        boolean isSlotTaken = appointmentRepository.existsByDoctorIdAndAppointmentDateAndTimeSlotAndStatusNot(
                request.getDoctorId(), request.getAppointmentDate(), request.getTimeSlot(), Appointment.AppointmentStatus.CANCELLED);
        
        if (isSlotTaken) {
            throw new BusinessException("Time slot is no longer available", "MSG01");
        }

        // 3. UC-02: Provide Patient Demographics (Create or Reuse)
        String phone = request.getPatient().getParentPhoneNumber();
        List<PatientResponse> existingPatients = patientService.searchPatientsByPhone(phone);
        
        Patient patient;
        if (existingPatients.isEmpty()) {
            // Create new patient profile
            PatientResponse newPatient = patientService.createPatient(request.getPatient());
            patient = patientRepository.findById(newPatient.getId()).get();
        } else {
            // Simplified matching: Just link to the first matching child of this parent
            // (In a real scenario, parent would select which child from a dropdown)
            patient = patientRepository.findById(existingPatients.get(0).getId()).get();
        }

        // 4. Create Appointment
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setTimeSlot(request.getTimeSlot());
        appointment.setStatus(Appointment.AppointmentStatus.PENDING); // Initial status
        
        // Note: 'reasonForVisit' goes to Visit entity later, or we can add it to Appointment if DB allows.
        // Wait, Appointment DB table doesn't have reason_for_visit, only Visit has 'symptoms'.
        // The API contract BookAppointmentRequest has reasonForVisit inside `PatientCreateRequest`.
        
        Appointment savedAppointment = appointmentRepository.save(appointment);
        
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentResponse registerWalkIn(WalkInAppointmentRequest request) {
        log.info("Processing walk-in registration for doctor: {}", request.getDoctorId());

        // 1. Verify Doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", request.getDoctorId().toString()));

        // 2. UC-07: Use existing patient management (lookup by phone)
        String phone = request.getPatient().getParentPhoneNumber();
        List<PatientResponse> existingPatients = patientService.searchPatientsByPhone(phone);
        
        Patient patient;
        if (existingPatients.isEmpty()) {
            PatientResponse newPatient = patientService.createPatient(request.getPatient());
            patient = patientRepository.findById(newPatient.getId()).get();
        } else {
            patient = patientRepository.findById(existingPatients.get(0).getId()).get();
        }

        // 3. Create Walk-in Appointment
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(java.time.LocalDate.now());
        
        // If slot not provided, use current time mapped to closest 30-min block
        String timeSlot = request.getTimeSlot();
        if (timeSlot == null || timeSlot.isBlank()) {
            java.time.LocalTime now = java.time.LocalTime.now();
            int minute = now.getMinute() < 30 ? 0 : 30;
            timeSlot = String.format("%02d:%02d", now.getHour(), minute);
        }
        appointment.setTimeSlot(timeSlot);
        appointment.setStatus(Appointment.AppointmentStatus.CHECKED_IN); // Skip Pending

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public List<AppointmentResponse> getTodayAppointments(java.time.LocalDate date, UUID doctorId, Appointment.AppointmentStatus status) {
        log.info("Fetching dashboard appointments for date: {}, doctor: {}, status: {}", date, doctorId, status);
        return appointmentRepository.findForDashboard(date, status, doctorId)
                .stream()
                .map(appointmentMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public AppointmentResponse updateAppointmentStatus(UUID appointmentId, AppointmentStatusUpdateRequest request) {
        log.info("Updating appointment: {} to status: {}", appointmentId, request.getStatus());

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId.toString()));

        // Requirement check: cancellation reason if status is CANCELLED
        if (request.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
            if (request.getCancellationReason() == null || request.getCancellationReason().isBlank()) {
                throw new com.hcms.common.exception.BusinessException("Cancellation reason is required when cancelling an appointment", "MSG04");
            }
            appointment.setCancellationReason(request.getCancellationReason());
        }

        appointment.setStatus(request.getStatus());
        Appointment updated = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsInRange(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        log.info("Fetching appointments between {} and {}", startDate, endDate);
        return appointmentRepository.findByAppointmentDateBetweenOrderByAppointmentDateAscTimeSlotAsc(startDate, endDate)
                .stream()
                .map(appointmentMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }
}
