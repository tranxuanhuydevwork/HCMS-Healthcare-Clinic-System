package com.hcms.modules.finance.service;

import com.hcms.modules.finance.dto.BillingResponse;

import java.util.UUID;

public interface FinanceService {
    BillingResponse generateInvoice(UUID visitId);
    BillingResponse getInvoiceByVisit(UUID visitId);
    BillingResponse markAsPaid(UUID billingId);
}
