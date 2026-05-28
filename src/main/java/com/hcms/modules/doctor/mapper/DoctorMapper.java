package com.hcms.modules.doctor.mapper;

import com.hcms.modules.doctor.dto.DoctorResponse;
import com.hcms.modules.doctor.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "fullName", source = "doctor.user.fullName")
    @Mapping(target = "email", source = "doctor.user.email")
    DoctorResponse toDoctorResponse(Doctor doctor);
}
