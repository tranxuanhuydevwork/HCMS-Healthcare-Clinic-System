package com.hcms.modules.clinical.entity;

import com.hcms.common.entity.BaseEntity;
import com.hcms.modules.booking.entity.Appointment;
import com.hcms.modules.finance.entity.Billing;
import com.hcms.modules.patient.entity.Patient;
import com.hcms.modules.pharmacy.entity.Prescription;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a clinical consultation visit.
 * ALL entities must extend BaseEntity.
 */
@Entity
@Table(name = "visits")
@Getter
@Setter
public class Visit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "clinical_notes", columnDefinition = "TEXT")
    private String clinicalNotes;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Billing billing;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VisitStatus status = VisitStatus.IN_PROGRESS;

    public enum VisitStatus {
        IN_PROGRESS, COMPLETED
    }
}
