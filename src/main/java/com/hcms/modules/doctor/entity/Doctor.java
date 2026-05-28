package com.hcms.modules.doctor.entity;

import java.math.BigDecimal;

import com.hcms.common.entity.BaseEntity;
import com.hcms.modules.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a Doctor profile.
 * Linked to a User entity with Role.DOCTOR.
 */
@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "specialty", nullable = false, length = 100)
    private String specialty;

    @Column(name = "license_number", unique = true, length = 50)
    private String licenseNumber;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "consultation_fee", nullable = false)
    private BigDecimal consultationFee = BigDecimal.ZERO;
}
