package com.hcms.modules.doctor.service;

import com.hcms.common.exception.ResourceNotFoundException;
import com.hcms.modules.doctor.dto.DoctorResponse;
import com.hcms.modules.doctor.mapper.DoctorMapper;
import com.hcms.modules.doctor.repository.DoctorRepository;
import com.hcms.modules.booking.dto.AvailableSlot;
import com.hcms.modules.booking.entity.Appointment;
import com.hcms.modules.booking.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<DoctorResponse> listActiveDoctors() {
        log.info("Fetching all active doctors from DoctorRepository");
        // We assume all doctors in the doctor table are active for now, 
        // or we could filter by user status if needed.
        return doctorRepository.findAll().stream()
                .filter(doctor -> !doctor.isDeleted())
                .map(doctorMapper::toDoctorResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableSlot> getDoctorAvailableSlots(UUID doctorId, LocalDate fromDate) {
        log.info("Fetching available slots for doctor: {} on date: {}", doctorId, fromDate);

        // Verify doctor exists
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId.toString()));

        // Fetch all non-cancelled appointments for this date
        // Note: appointmentRepository needs to be updated to search by doctor_id (the Doctor entity ID)
        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorIdAndAppointmentDateAndStatusNot(
                doctorId, fromDate, Appointment.AppointmentStatus.CANCELLED);

        // Create a list of booked times to exclusion
        List<String> bookedTimes = bookedAppointments.stream()
                .map(Appointment::getTimeSlot)
                .toList();

        List<AvailableSlot> availableSlots = new ArrayList<>();
        
        // Hardcoded standard clinic hours: 08:00 to 17:00, 30-min slots
        String[] allPossibleSlots = {
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", 
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
        };

        for (String slot : allPossibleSlots) {
            if (!bookedTimes.contains(slot)) {
                availableSlots.add(AvailableSlot.builder()
                        .date(fromDate)
                        .timeSlot(slot)
                        .build());
            }
        }

        return availableSlots;
    }
}
