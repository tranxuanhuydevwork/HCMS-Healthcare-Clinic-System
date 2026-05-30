package com.hcms.modules.clinical.dto;

import com.hcms.modules.clinical.entity.Visit.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponse {
    private UUID id;
    private UUID patientId;
    private String patientName;
    private UUID appointmentId;
    private LocalDateTime visitDate;
    private String symptoms;
    private String diagnosis;
    private String clinicalNotes;
    private VisitStatus status;
    private LocalDateTime createdAt;
}
