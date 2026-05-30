package com.hcms.modules.pharmacy.service;

import com.hcms.modules.pharmacy.dto.PrescriptionRequest;
import com.hcms.modules.pharmacy.dto.PrescriptionResponse;

import java.util.List;
import java.util.UUID;

public interface PharmacyService {
    List<PrescriptionResponse> issuePrescription(UUID visitId, List<PrescriptionRequest> requests);
    List<PrescriptionResponse> getVisitPrescriptions(UUID visitId);
}
