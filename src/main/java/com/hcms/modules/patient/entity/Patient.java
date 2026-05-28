package com.hcms.modules.patient.entity;

import com.hcms.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@SQLRestriction("is_deleted = false")
public class Patient extends BaseEntity {

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "parent_phone_number", nullable = false, length = 20)
    private String parentPhoneNumber;

    @Column(name = "parent_email", length = 100)
    private String parentEmail;

    @Column(name = "address")
    private String address;

    @Column(name = "height_cm", precision = 5, scale = 2)
    private BigDecimal heightCm;

    @Column(name = "weight_kg", precision = 5, scale = 2)
    private BigDecimal weightKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Column(name = "allergies", length = 500)
    private String allergies;

    @Column(name = "chronic_conditions", length = 500)
    private String chronicConditions;

    @Column(name = "vaccination_notes", length = 500)
    private String vaccinationNotes;
}
