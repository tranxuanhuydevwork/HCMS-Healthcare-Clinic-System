package com.hcms.modules.clinical.service;

import com.hcms.modules.clinical.dto.VisitCreateRequest;
import com.hcms.modules.clinical.dto.VisitResponse;

import java.util.List;
import java.util.UUID;

public interface ClinicalService {
    VisitResponse recordConsultation(VisitCreateRequest request);
    List<VisitResponse> getPatientHistory(UUID patientId);
    VisitResponse getVisitDetails(UUID visitId);
    void finalizeVisit(UUID visitId);
}
