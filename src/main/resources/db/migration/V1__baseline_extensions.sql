-- -----------------------------------------------------------------------------
-- V1: Baseline Database Configuration
-- Role: Setting up character set, collation and global environments.
-- -----------------------------------------------------------------------------

-- Ensure the database uses utf8mb4 for full Unicode support (including emojis)
-- Collation utf8mb4_unicode_ci is recommended for accurate sorting in Vietnamese.

SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- Note: MySQL 8.x handles UUIDs natively via UUID() functions or application-layer generation.
-- No specific extensions (like uuid-ossp in PG) are required.
