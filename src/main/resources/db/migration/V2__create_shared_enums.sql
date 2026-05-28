-- -----------------------------------------------------------------------------
-- V2: Shared Enumerations and Global States
-- Role: Documenting shared state enums that span multiple modules.
-- In MySQL, these are typically defined inline within CREATE TABLE statements.
-- -----------------------------------------------------------------------------

-- 1. Authentication Roles
-- Values: 'ADMIN', 'DOCTOR', 'RECEPTIONIST', 'PARENT'

-- 2. System Status
-- Values: 'ACTIVE', 'INACTIVE', 'SUSPENDED'

-- 3. Appointment Status (from physical_schema.sql)
-- Values: 'PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED'

-- 4. Visit Status (from physical_schema.sql)
-- Values: 'IN_PROGRESS', 'COMPLETED'

-- 5. Payment Status (from physical_schema.sql)
-- Values: 'UNPAID', 'PAID'
