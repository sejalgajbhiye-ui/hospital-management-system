package com.hospital.hospital_management_system.service;

import com.hospital.hospital_management_system.dto.AssignInsuranceRequestDto;
import com.hospital.hospital_management_system.dto.InsuranceResponseDto;
import com.hospital.hospital_management_system.dto.PatientRequestDto;
import com.hospital.hospital_management_system.dto.PatientResponseDto;
import com.hospital.hospital_management_system.entity.InsuranceEntity;
import com.hospital.hospital_management_system.entity.PatientEntity;
import com.hospital.hospital_management_system.repository.InsuranceRepository;
import com.hospital.hospital_management_system.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.hospital_management_system.exception.ResourceNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;


    // GET all patients
    public List<PatientResponseDto> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }


    // GET patient by ID
    public PatientResponseDto getPatientById(Long id) {

        PatientEntity patient =
                patientRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id: " + id
                                )
                        );

        return convertToResponseDto(patient);
    }


    // CREATE patient
    public PatientResponseDto createPatient(
            PatientRequestDto dto) {

        PatientEntity patient = new PatientEntity();

        patient.setName(dto.getName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setEmail(dto.getEmail());
        patient.setGender(dto.getGender());
        patient.setBloodGroup(dto.getBloodGroup());

        PatientEntity savedPatient =
                patientRepository.save(patient);

        return convertToResponseDto(savedPatient);
    }


    // UPDATE patient
    public PatientResponseDto updatePatient(
            Long id,
            PatientRequestDto dto) {

        PatientEntity patient =
                patientRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id: " + id
                                )
                        );

        patient.setName(dto.getName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setEmail(dto.getEmail());
        patient.setGender(dto.getGender());
        patient.setBloodGroup(dto.getBloodGroup());

        PatientEntity updatedPatient =
                patientRepository.save(patient);

        return convertToResponseDto(updatedPatient);
    }


    // DELETE patient
    public void deletePatient(Long id) {

        PatientEntity patient =
                patientRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id: " + id
                                )
                        );

        patientRepository.delete(patient);
    }


    // ASSIGN insurance to patient
    @Transactional
    public PatientResponseDto assignInsuranceToPatient(
            Long pid,
            AssignInsuranceRequestDto requestDto) {

        PatientEntity patientEntity =
                patientRepository.findById(pid)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id: " + pid
                                )
                        );

        InsuranceEntity insuranceEntity =
                insuranceRepository.findById(
                                requestDto.getInsuranceId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance not found with id: "
                                                + requestDto.getInsuranceId()
                                )
                        );

        patientEntity.setInsuranceEntity(insuranceEntity);

        // Maintain bidirectional consistency
        insuranceEntity.setPatientEntity(patientEntity);

        // No explicit save() required because
        // patientEntity is a managed entity.

        return convertToResponseDto(patientEntity);
    }


    // DISSOCIATE insurance from patient
    @Transactional
    public PatientResponseDto dissociateInsuranceFromPatient(
            Long pid) {

        PatientEntity patientEntity =
                patientRepository.findById(pid)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id: " + pid
                                )
                        );

        InsuranceEntity insuranceEntity =
                patientEntity.getInsuranceEntity();

        patientEntity.setInsuranceEntity(null);

        if (insuranceEntity != null) {
            insuranceEntity.setPatientEntity(null);
        }

        return convertToResponseDto(patientEntity);
    }


    // Entity → Response DTO
    private PatientResponseDto convertToResponseDto(
            PatientEntity patient) {

        InsuranceResponseDto insuranceDto = null;

        if (patient.getInsuranceEntity() != null) {

            InsuranceEntity insurance =
                    patient.getInsuranceEntity();

            insuranceDto = InsuranceResponseDto.builder()
                    .id(insurance.getId())
                    .policyNumber(insurance.getPolicyNumber())
                    .provider(insurance.getProvider())
                    .validUntil(insurance.getValidUntil())
                    .createdAt(insurance.getCreatedAt())
                    .build();
        }

        return PatientResponseDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .birthDate(patient.getBirthDate())
                .email(patient.getEmail())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .createdAt(patient.getCreatedAt())
                .insurance(insuranceDto)
                .build();
    }
}