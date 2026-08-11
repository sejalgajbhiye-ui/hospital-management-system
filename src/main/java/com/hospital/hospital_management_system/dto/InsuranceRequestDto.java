package com.hospital.hospital_management_system.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InsuranceRequestDto {

    @NotBlank(message = "Policy number is required")
    private String policyNumber;

    @NotBlank(message = "Insurance provider is required")
    private String provider;

    @NotNull(message = "Valid until date is required")
    @Future(message = "Insurance valid until date must be in the future")
    private LocalDate validUntil;
}