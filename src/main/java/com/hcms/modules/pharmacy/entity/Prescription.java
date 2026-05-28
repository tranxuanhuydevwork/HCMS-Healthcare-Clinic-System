package com.hcms.modules.pharmacy.entity;

import com.hcms.common.entity.BaseEntity;
import com.hcms.modules.clinical.entity.Visit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing an electronic prescription for a visit.
 * ALL entities must extend BaseEntity.
 */
@Entity
@Table(name = "prescriptions")
@Getter
@Setter
public class Prescription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @Column(name = "medicine_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String medicineName;

    @Column(name = "dosage_instruction", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String dosageInstruction;

    @Column(name = "duration", nullable = false)
    private String duration;
}
