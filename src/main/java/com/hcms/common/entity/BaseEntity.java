package com.hcms.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base abstract class for all domain entities in the HCMS project.
 * Provides primary key generation using UUID and comprehensive auditing
 * support.
 * 
 * ALL domain entities must extend this class to ensure consistency in ID
 * handling,
 * audit tracking, and soft-delete capabilities.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity {

    /**
     * Unique identifier for the entity.
     * Uses UUID strategy for Hibernate 6+ to ensure global uniqueness and prevent
     * ID enumeration.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
    private UUID id;

    /**
     * Timestamp when the entity was first persisted.
     */
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the entity was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Username or System ID of the user who created this entity.
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    /**
     * Username or System ID of the user who last modified this entity.
     */
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * Soft delete indicator. Default is false.
     */
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    /**
     * Timestamp when the entity was soft-deleted.
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
