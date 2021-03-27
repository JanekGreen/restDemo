package com.wojcik.restDemo.mapstruct;

import com.wojcik.restDemo.dto.DoctorDto;
import com.wojcik.restDemo.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toDoctor(DoctorDto doctorDto);
    DoctorDto toDoctorDto(Doctor doctor);
}
