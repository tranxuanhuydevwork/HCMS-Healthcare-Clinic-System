package com.hcms.modules.clinical.mapper;

import com.hcms.modules.clinical.dto.VisitResponse;
import com.hcms.modules.clinical.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.fullName", target = "patientName")
    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "createdAt", target = "visitDate")
    VisitResponse toResponse(Visit visit);
}
