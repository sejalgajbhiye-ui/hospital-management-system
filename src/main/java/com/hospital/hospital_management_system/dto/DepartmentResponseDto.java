package com.hospital.hospital_management_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class DepartmentResponseDto {

    private Long id;

    private String name;

    private Set<Long> doctorIds;
}