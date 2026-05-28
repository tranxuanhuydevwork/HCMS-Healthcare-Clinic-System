package com.hcms.modules.finance.entity;

import com.hcms.common.entity.BaseEntity;
import com.hcms.modules.clinical.entity.Visit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entity representing a billing invoice for medical services.
 * ALL entities must extend BaseEntity.
 */
@Entity
@Table(name = "billings")
@Getter
@Setter
public class Billing extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @Column(name = "consultation_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "billing_date", nullable = false)
    private java.time.LocalDateTime billingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    public enum PaymentStatus {
        PAID, UNPAID
    }
}
