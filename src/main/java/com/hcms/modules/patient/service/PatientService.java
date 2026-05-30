package com.hcms.modules.patient.service;

import com.hcms.modules.patient.dto.PatientCreateRequest;
import com.hcms.modules.patient.dto.PatientResponse;
import com.hcms.modules.patient.dto.PatientUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    
    PatientResponse createPatient(PatientCreateRequest request);

    PatientResponse getPatientById(UUID id);

    PatientResponse updatePatientProfile(UUID id, PatientUpdateRequest request);

    List<PatientResponse> searchPatientsByPhone(String phoneNumber);
}
