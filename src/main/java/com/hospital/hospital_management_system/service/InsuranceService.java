package com.hospital.hospital_management_system.service;

import com.hospital.hospital_management_system.dto.InsuranceRequestDto;
import com.hospital.hospital_management_system.dto.InsuranceResponseDto;
import com.hospital.hospital_management_system.entity.InsuranceEntity;
import com.hospital.hospital_management_system.exception.ResourceNotFoundException;
import com.hospital.hospital_management_system.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;


    // GET all insurance records
    public List<InsuranceResponseDto> getAllInsurances() {

        return insuranceRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }


    // GET insurance by ID
    public InsuranceResponseDto getInsuranceById(Long id) {

        InsuranceEntity insurance =
                insuranceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance not found with id: " + id
                                )
                        );

        return convertToResponseDto(insurance);
    }


    // CREATE insurance
    public InsuranceResponseDto createInsurance(
            InsuranceRequestDto dto) {

        InsuranceEntity insurance =
                new InsuranceEntity();

        insurance.setPolicyNumber(
                dto.getPolicyNumber()
        );

        insurance.setProvider(
                dto.getProvider()
        );

        insurance.setValidUntil(
                dto.getValidUntil()
        );

        InsuranceEntity savedInsurance =
                insuranceRepository.save(insurance);

        return convertToResponseDto(savedInsurance);
    }


    // UPDATE insurance
    public InsuranceResponseDto updateInsurance(
            Long id,
            InsuranceRequestDto dto) {

        InsuranceEntity insurance =
                insuranceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance not found with id: "
                                                + id
                                )
                        );

        insurance.setPolicyNumber(
                dto.getPolicyNumber()
        );

        insurance.setProvider(
                dto.getProvider()
        );

        insurance.setValidUntil(
                dto.getValidUntil()
        );

        InsuranceEntity updatedInsurance =
                insuranceRepository.save(insurance);

        return convertToResponseDto(updatedInsurance);
    }


    // DELETE insurance
    public void deleteInsurance(Long id) {

        InsuranceEntity insurance =
                insuranceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance not found with id: "
                                                + id
                                )
                        );

        insuranceRepository.delete(insurance);
    }


    // Entity → Response DTO
    private InsuranceResponseDto convertToResponseDto(
            InsuranceEntity insurance) {

        return InsuranceResponseDto.builder()
                .id(insurance.getId())
                .policyNumber(
                        insurance.getPolicyNumber()
                )
                .provider(
                        insurance.getProvider()
                )
                .validUntil(
                        insurance.getValidUntil()
                )
                .createdAt(
                        insurance.getCreatedAt()
                )
                .build();
    }
}