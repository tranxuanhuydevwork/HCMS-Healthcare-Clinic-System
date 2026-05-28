-- -----------------------------------------------------------------------------
-- V6: Create Doctors Profile Table
-- Links specific clinical profile data to Users with Role.DOCTOR.
-- -----------------------------------------------------------------------------

CREATE TABLE `doctors` (
    `id`                VARCHAR(36) NOT NULL,
    `user_id`           VARCHAR(36) NOT NULL,
    
    -- Business Fields
    `specialty`         VARCHAR(100) NOT NULL,
    `license_number`    VARCHAR(50) NULL,
    `biography`         TEXT NULL,
    `consultation_fee`  DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    
    -- Audit Columns
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by`        VARCHAR(100) NULL,
    `updated_by`        VARCHAR(100) NULL,
    `is_deleted`        BOOLEAN NOT NULL DEFAULT FALSE,
    `deleted_at`        TIMESTAMP NULL DEFAULT NULL,
    
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_doctors_user_id` (`user_id`),
    UNIQUE KEY `uk_doctors_license` (`license_number`),
    CONSTRAINT `fk_doctors_users` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX `idx_doctors_user_id` ON `doctors`(`user_id`);

-- =============================================================================
-- SAMPLE DATA INSERTION
-- Link BS. Minh (from V5) to a Doctor Profile
-- =============================================================================

INSERT INTO `doctors` (`id`, `user_id`, `specialty`, `license_number`, `biography`, `consultation_fee`) VALUES
('dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', 'Pediatrics', 'DOC-123456', 'Senior Pediatrician with over 10 years of experience in child healthcare.', 150000.00);

-- =============================================================================
-- SCHEMA MIGRATION: Update Appointments to reference Doctors table
-- =============================================================================

-- 1. Remove the old foreign key referencing users
ALTER TABLE `appointments` DROP FOREIGN KEY `fk_appointments_users_doc`;

-- 2. Update existing appointments to use the new doctor_id from doctors table
-- Mapping u2222222-2222-2222-2222-222222222222 -> d1111111-1111-1111-1111-111111111111
UPDATE `appointments` SET `doctor_id` = 'dddddddd-dddd-dddd-dddd-dddddddddddd' 
WHERE `doctor_id` = '22222222-2222-2222-2222-222222222222';

-- 3. Add the new foreign key referencing doctors table
ALTER TABLE `appointments` 
ADD CONSTRAINT `fk_appointments_doctors` 
FOREIGN KEY (`doctor_id`) REFERENCES `doctors`(`id`) 
ON DELETE RESTRICT ON UPDATE CASCADE;

-- Update Comment
ALTER TABLE `doctors` COMMENT = 'Doctor specific profiles linked to users';
