package com.hcms.modules.finance.mapper;

import com.hcms.modules.finance.dto.BillingResponse;
import com.hcms.modules.finance.entity.Billing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillingMapper {

    @Mapping(source = "visit.id", target = "visitId")
    @Mapping(source = "visit.patient.fullName", target = "patientName")
    BillingResponse toResponse(Billing billing);
}
