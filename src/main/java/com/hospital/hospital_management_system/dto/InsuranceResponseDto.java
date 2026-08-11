package com.hospital.hospital_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceResponseDto {

    private Long id;

    private String policyNumber;

    private String provider;

    private LocalDate validUntil;

    private LocalDateTime createdAt;
}