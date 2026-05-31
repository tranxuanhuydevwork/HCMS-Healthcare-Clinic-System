package com.hcms.modules.finance.dto;

import com.hcms.modules.finance.entity.Billing.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponse {
    private UUID id;
    private UUID visitId;
    private String patientName;
    private BigDecimal consultationFee;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private LocalDateTime billingDate;
}
