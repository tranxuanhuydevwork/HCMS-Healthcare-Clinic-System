package com.hcms.modules.pharmacy.service;

import com.hcms.common.exception.ResourceNotFoundException;
import com.hcms.modules.clinical.entity.Visit;
import com.hcms.modules.clinical.repository.VisitRepository;
import com.hcms.modules.finance.service.FinanceService;
import com.hcms.modules.pharmacy.dto.PrescriptionRequest;
import com.hcms.modules.pharmacy.dto.PrescriptionResponse;
import com.hcms.modules.pharmacy.entity.Prescription;
import com.hcms.modules.pharmacy.mapper.PrescriptionMapper;
import com.hcms.modules.pharmacy.repository.PrescriptionRepository;
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
public class PharmacyServiceImpl implements PharmacyService {

    private final PrescriptionRepository prescriptionRepository;
    private final VisitRepository visitRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final FinanceService financeService;

    @Override
    @Transactional
    public List<PrescriptionResponse> issuePrescription(UUID visitId, List<PrescriptionRequest> requests) {
        log.info("Issuing {} prescriptions for visit: {}", requests.size(), visitId);

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit", "id", visitId.toString()));

        // Clear existing prescriptions for this visit if re-issuing
        List<Prescription> existing = prescriptionRepository.findByVisitId(visitId);
        prescriptionRepository.deleteAll(existing);

        List<Prescription> prescriptions = requests.stream().map(req -> {
            Prescription p = new Prescription();
            p.setVisit(visit);
            p.setMedicineName(req.getMedicineName());
            p.setDosageInstruction(req.getDosageInstruction());
            p.setDuration(req.getDuration());
            return p;
        }).collect(Collectors.toList());

        List<Prescription> saved = prescriptionRepository.saveAll(prescriptions);
        
        // Trigger Billing Generation
        financeService.generateInvoice(visitId);

        return saved.stream().map(prescriptionMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getVisitPrescriptions(UUID visitId) {
        return prescriptionRepository.findByVisitId(visitId)
                .stream()
                .map(prescriptionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
