package com.hcms.modules.clinical.repository;

import com.hcms.modules.clinical.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VisitRepository extends JpaRepository<Visit, UUID> {
    
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"patient", "appointment"})
    List<Visit> findByPatientIdOrderByCreatedAtDesc(UUID patientId);
}
