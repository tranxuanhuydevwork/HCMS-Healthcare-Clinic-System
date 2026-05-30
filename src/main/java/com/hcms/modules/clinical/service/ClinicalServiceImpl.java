package com.hcms.modules.clinical.service;

import com.hcms.common.exception.ResourceNotFoundException;
import com.hcms.modules.booking.entity.Appointment;
import com.hcms.modules.booking.repository.AppointmentRepository;
import com.hcms.modules.clinical.dto.VisitCreateRequest;
import com.hcms.modules.clinical.dto.VisitResponse;
import com.hcms.modules.clinical.entity.Visit;
import com.hcms.modules.clinical.mapper.VisitMapper;
import com.hcms.modules.clinical.repository.VisitRepository;
import com.hcms.modules.patient.entity.Patient;
import com.hcms.modules.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClinicalServiceImpl implements ClinicalService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final VisitMapper visitMapper;

    @Override
    @Transactional
    public VisitResponse recordConsultation(VisitCreateRequest request) {
        log.info("Recording consultation for patient: {}", request.getPatientId());

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId().toString()));

        Visit visit = new Visit();
        visit.setPatient(patient);
        visit.setSymptoms(request.getSymptoms());
        visit.setDiagnosis(request.getDiagnosis());
        visit.setClinicalNotes(request.getClinicalNotes());
        visit.setStatus(Visit.VisitStatus.IN_PROGRESS);

        if (request.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", request.getAppointmentId().toString()));
            visit.setAppointment(appointment);
            
            // Update appointment status to IN_PROGRESS
            appointment.setStatus(Appointment.AppointmentStatus.IN_PROGRESS);
            appointmentRepository.save(appointment);
        }

        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toResponse(savedVisit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitResponse> getPatientHistory(UUID patientId) {
        log.info("Fetching history for patient: {}", patientId);
        return visitRepository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(visitMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VisitResponse getVisitDetails(UUID visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit", "id", visitId.toString()));
        return visitMapper.toResponse(visit);
    }

    @Override
    @Transactional
    public void finalizeVisit(UUID visitId) {
        log.info("Finalizing visit: {}", visitId);
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit", "id", visitId.toString()));
        
        visit.setStatus(Visit.VisitStatus.COMPLETED);
        
        if (visit.getAppointment() != null) {
            visit.getAppointment().setStatus(Appointment.AppointmentStatus.COMPLETED);
            appointmentRepository.save(visit.getAppointment());
        }
        
        visitRepository.save(visit);
    }
}
