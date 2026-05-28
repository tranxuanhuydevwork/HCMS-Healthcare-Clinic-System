-- -----------------------------------------------------------------------------
-- V3: Create Core Users Table
-- Role: Foundation table for Authentication and Security modules.
-- Matching: com.hcms.common.entity.BaseEntity
-- -----------------------------------------------------------------------------

CREATE TABLE `users` (
    -- Primary Key: UUID as VARCHAR(36) to match Hibernate UUID strategy
    `id`                VARCHAR(36) NOT NULL,
    
    -- Business Fields
    `username`          VARCHAR(50) NOT NULL,
    `email`             VARCHAR(100) NOT NULL,
    `password_hash`     VARCHAR(255) NOT NULL,
    `full_name`         VARCHAR(100) NOT NULL,
    `role`              ENUM('ADMIN', 'DOCTOR', 'RECEPTIONIST', 'PARENT') NOT NULL,
    `status`            ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') NOT NULL DEFAULT 'ACTIVE',
    
    -- Audit Columns (matching BaseEntity.java)
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by`        VARCHAR(100) NULL,
    `updated_by`        VARCHAR(100) NULL,
    
    -- Soft Delete columns
    `is_deleted`        BOOLEAN NOT NULL DEFAULT FALSE,
    `deleted_at`        TIMESTAMP NULL DEFAULT NULL,
    
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_users_username` (`username`),
    UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Performance Indexes
-- -----------------------------------------------------------------------------

-- Index on email for fast login lookups
CREATE INDEX `idx_users_email` ON `users`(`email`);

-- Index on role for access control filtering
CREATE INDEX `idx_users_role` ON `users`(`role`);

-- Index on status for active account filtering
CREATE INDEX `idx_users_status` ON `users`(`status`);

-- Index for soft-delete filtering
-- Note: MySQL 8.x doesn't support 'WHERE is_deleted = FALSE' partial indexes,
-- but a standard index on is_deleted helps with filtering deleted records.
CREATE INDEX `idx_users_is_deleted` ON `users`(`is_deleted`);

-- Commenting for schema clarity
ALTER TABLE `users` COMMENT = 'Core users table for clinic staff and parent portal users';
