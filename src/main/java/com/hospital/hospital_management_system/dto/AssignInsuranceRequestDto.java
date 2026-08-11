package com.hospital.hospital_management_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignInsuranceRequestDto {

    @NotNull(message = "Insurance ID is required")
    private Long insuranceId;
}