package com.hcms.modules.pharmacy.mapper;

import com.hcms.modules.pharmacy.dto.PrescriptionResponse;
import com.hcms.modules.pharmacy.entity.Prescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    PrescriptionResponse toResponse(Prescription prescription);
}
