package com.hcms.modules.finance.service;

import com.hcms.common.exception.ResourceNotFoundException;
import com.hcms.modules.clinical.entity.Visit;
import com.hcms.modules.clinical.repository.VisitRepository;
import com.hcms.modules.finance.dto.BillingResponse;
import com.hcms.modules.finance.entity.Billing;
import com.hcms.modules.finance.mapper.BillingMapper;
import com.hcms.modules.finance.repository.BillingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final BillingRepository billingRepository;
    private final VisitRepository visitRepository;
    private final BillingMapper billingMapper;

    private static final BigDecimal DEFAULT_CONSULTATION_FEE = new BigDecimal("150000.00");

    @Override
    @Transactional
    public BillingResponse generateInvoice(UUID visitId) {
        log.info("Generating invoice for visit: {}", visitId);

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit", "id", visitId.toString()));

        // Check if invoice already exists
        return billingRepository.findByVisitId(visitId)
                .map(billingMapper::toResponse)
                .orElseGet(() -> {
                    Billing billing = new Billing();
                    billing.setVisit(visit);
                    billing.setConsultationFee(DEFAULT_CONSULTATION_FEE);
                    
                    // Simple logic: total = consultation fee + 20,000 per medication (mocking price)
                    long medicationCount = visit.getPrescriptions().size();
                    BigDecimal medicationCost = new BigDecimal(medicationCount * 20000.00);
                    
                    billing.setTotalAmount(DEFAULT_CONSULTATION_FEE.add(medicationCost));
                    billing.setPaymentStatus(Billing.PaymentStatus.UNPAID);
                    billing.setBillingDate(LocalDateTime.now());

                    Billing savedBilling = billingRepository.save(billing);
                    return billingMapper.toResponse(savedBilling);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public BillingResponse getInvoiceByVisit(UUID visitId) {
        Billing billing = billingRepository.findByVisitId(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing", "visitId", visitId.toString()));
        return billingMapper.toResponse(billing);
    }

    @Override
    @Transactional
    public BillingResponse markAsPaid(UUID billingId) {
        log.info("Marking billing {} as paid", billingId);
        Billing billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing", "id", billingId.toString()));
        
        billing.setPaymentStatus(Billing.PaymentStatus.PAID);
        Billing updated = billingRepository.save(billing);
        return billingMapper.toResponse(updated);
    }
}
