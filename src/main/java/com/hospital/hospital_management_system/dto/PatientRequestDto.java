package com.hospital.hospital_management_system.dto;

import com.hospital.hospital_management_system.entity.type.BloodGroupType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDto {

    @NotBlank(message = "Patient name is required")
    private String name;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Blood group is required")
    private BloodGroupType bloodGroup;
}