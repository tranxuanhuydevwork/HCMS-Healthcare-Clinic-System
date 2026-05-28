-- -----------------------------------------------------------------------------
-- V4: Create Patients Table
-- Module: Patient
-- Role: Central anchor for clinical data and appointments.
-- -----------------------------------------------------------------------------

CREATE TABLE `patients` (
    `id`                    VARCHAR(36) NOT NULL,
    
    -- Demographics
    `full_name`             VARCHAR(100) NOT NULL,
    `date_of_birth`         DATE NOT NULL,
    `gender`                ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
    `parent_phone_number`   VARCHAR(20) NOT NULL,
    `parent_email`          VARCHAR(100) NULL,
    `address`               VARCHAR(255) NULL,
    
    -- Medical Profile (Nullable for initial booking, supplemented later)
    `height_cm`             DECIMAL(5, 2) NULL,
    `weight_kg`             DECIMAL(5, 2) NULL,
    `blood_type`            ENUM('A', 'B', 'AB', 'O', 'UNKNOWN') NULL DEFAULT 'UNKNOWN',
    `allergies`             VARCHAR(500) NULL,
    `chronic_conditions`    VARCHAR(500) NULL,
    `vaccination_notes`     VARCHAR(500) NULL,
    
    -- Audit Columns (BaseEntity)
    `created_at`            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by`            VARCHAR(100) NULL,
    `updated_by`            VARCHAR(100) NULL,
    `is_deleted`            BOOLEAN NOT NULL DEFAULT FALSE,
    `deleted_at`            TIMESTAMP NULL DEFAULT NULL,
    
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Performance Indexes
CREATE INDEX `idx_patients_parent_phone` ON `patients`(`parent_phone_number`);
CREATE INDEX `idx_patients_full_name` ON `patients`(`full_name`);
CREATE INDEX `idx_patients_is_deleted` ON `patients`(`is_deleted`);

ALTER TABLE `patients` COMMENT = 'Core patient record holding demographics and general medical history';
