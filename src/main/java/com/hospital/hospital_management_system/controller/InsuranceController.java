package com.hospital.hospital_management_system.controller;

import com.hospital.hospital_management_system.dto.InsuranceRequestDto;
import com.hospital.hospital_management_system.dto.InsuranceResponseDto;
import com.hospital.hospital_management_system.service.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    // CREATE insurance
    @PostMapping
    public ResponseEntity<InsuranceResponseDto> createInsurance(@Valid @RequestBody InsuranceRequestDto insuranceRequestDto) {
        InsuranceResponseDto createdInsurance = insuranceService.createInsurance(insuranceRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdInsurance);
    }
}