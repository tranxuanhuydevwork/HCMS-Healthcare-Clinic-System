-- -----------------------------------------------------------------------------
-- V7: Seed Additional Sample Data for Clinical, Pharmacy, and Finance testing
-- -----------------------------------------------------------------------------

-- Note: We use valid hex UUIDs for testing.

-- 1. Add some more appointments for the queue (UC-10)
INSERT INTO `appointments` (`id`, `patient_id`, `doctor_id`, `appointment_date`, `time_slot`, `status`) VALUES
('00000000-0000-0000-0000-000000000011', '00000000-0000-0000-0000-000000000001', 'dddddddd-dddd-dddd-dddd-dddddddddddd', CURRENT_DATE, '08:00', 'CHECKED_IN'),
('00000000-0000-0000-0000-000000000012', '00000000-0000-0000-0000-000000000002', 'dddddddd-dddd-dddd-dddd-dddddddddddd', CURRENT_DATE, '08:30', 'CHECKED_IN'),
('00000000-0000-0000-0000-000000000013', '00000000-0000-0000-0000-000000000001', 'dddddddd-dddd-dddd-dddd-dddddddddddd', CURRENT_DATE, '09:00', 'CONFIRMED');

-- 2. Add a Visit that is COMPLETED to test history (UC-11)
INSERT INTO `visits` (`id`, `patient_id`, `appointment_id`, `visit_date`, `symptoms`, `diagnosis`, `clinical_notes`, `status`) VALUES
('00000000-0000-0000-0000-000000000021', '00000000-0000-0000-0000-000000000001', NULL, '2026-04-15 09:00:00', 'Mild cough', 'Common Cold', 'Advised warm water and rest.', 'COMPLETED');

-- 3. Add Prescription for that visit
INSERT INTO `prescriptions` (`id`, `visit_id`, `medicine_name`, `dosage_instruction`, `duration`) VALUES
('00000000-0000-0000-0000-000000000031', '00000000-0000-0000-0000-000000000021', 'Vitamin C', '1 tablet daily', '30 days');

-- 4. Add Billing for that visit (Paid)
INSERT INTO `billings` (`id`, `visit_id`, `consultation_fee`, `total_amount`, `payment_status`, `billing_date`) VALUES
('00000000-0000-0000-0000-000000000041', '00000000-0000-0000-0000-000000000021', 150000.00, 150000.00, 'PAID', '2026-04-15 09:30:00');
