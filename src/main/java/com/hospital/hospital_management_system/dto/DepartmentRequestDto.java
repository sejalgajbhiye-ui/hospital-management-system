package com.hospital.hospital_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DepartmentRequestDto {

    @NotBlank(message = "Department name is required")
    private String name;

    @NotEmpty(message = "At least one doctor is required")
    private Set<Long> doctorIds;
}