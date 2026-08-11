package com.hospital.hospital_management_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class DoctorResponseDto {

    private Long id;

    private String name;

    private String specialization;

    private String email;

    private Set<Long> departmentIds;
}