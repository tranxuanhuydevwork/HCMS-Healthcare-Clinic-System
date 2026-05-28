package com.hcms.modules.patient.mapper;

import com.hcms.modules.patient.dto.PatientCreateRequest;
import com.hcms.modules.patient.dto.PatientResponse;
import com.hcms.modules.patient.dto.PatientUpdateRequest;
import com.hcms.modules.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "heightCm", ignore = true)
    @Mapping(target = "weightKg", ignore = true)
    @Mapping(target = "bloodType", ignore = true)
    @Mapping(target = "allergies", ignore = true)
    @Mapping(target = "chronicConditions", ignore = true)
    @Mapping(target = "vaccinationNotes", ignore = true)
    Patient toEntity(PatientCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "parentPhoneNumber", ignore = true)
    @Mapping(target = "parentEmail", ignore = true)
    @Mapping(target = "address", ignore = true)
    void updateEntityFromDto(PatientUpdateRequest request, @MappingTarget Patient patient);

    PatientResponse toResponse(Patient patient);
}
