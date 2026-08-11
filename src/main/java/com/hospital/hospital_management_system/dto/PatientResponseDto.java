package com.hospital.hospital_management_system.dto;

import com.hospital.hospital_management_system.dto.InsuranceResponseDto;
import com.hospital.hospital_management_system.entity.type.BloodGroupType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PatientResponseDto {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private String email;

    private String gender;

    private BloodGroupType bloodGroup;

    private LocalDateTime createdAt;

    // Complete insurance object
    private InsuranceResponseDto insurance;
}