package com.hcms.modules.patient.repository;

import com.hcms.modules.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    
    /**
     * Find patients by parent's phone number.
     * Used for Receptionist walk-in search (UC-07) and deduplication.
     */
    List<Patient> findByParentPhoneNumber(String parentPhoneNumber);
}
