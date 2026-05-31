package com.hcms.modules.booking.mapper;

import com.hcms.modules.booking.dto.AppointmentResponse;
import com.hcms.modules.booking.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.fullName", target = "patientName")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.user.fullName", target = "doctorName")
    AppointmentResponse toResponse(Appointment appointment);
}
