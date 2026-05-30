package com.hcms.modules.patient.service;

import com.hcms.common.exception.ResourceNotFoundException;
import com.hcms.modules.patient.dto.PatientCreateRequest;
import com.hcms.modules.patient.dto.PatientResponse;
import com.hcms.modules.patient.dto.PatientUpdateRequest;
import com.hcms.modules.patient.entity.Patient;
import com.hcms.modules.patient.mapper.PatientMapper;
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
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponse createPatient(PatientCreateRequest request) {
        log.info("Creating new patient profile for: {}", request.getFullName());
        
        Patient patient = patientMapper.toEntity(request);
        Patient savedPatient = patientRepository.save(patient);
        
        return patientMapper.toResponse(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(UUID id) {
        log.debug("Fetching patient by id: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));

        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional
    public PatientResponse updatePatientProfile(UUID id, PatientUpdateRequest request) {
        log.info("Updating medical profile for patient id: {}", id);
        
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));
                
        patientMapper.updateEntityFromDto(request, patient);
        Patient updatedPatient = patientRepository.save(patient);
        
        return patientMapper.toResponse(updatedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> searchPatientsByPhone(String phoneNumber) {
        log.debug("Searching patients by phone number: {}", phoneNumber);
        
        return patientRepository.findByParentPhoneNumber(phoneNumber).stream()
                .map(patientMapper::toResponse)
                .collect(Collectors.toList());
    }
}
