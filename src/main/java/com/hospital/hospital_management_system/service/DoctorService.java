package com.hospital.hospital_management_system.service;

import com.hospital.hospital_management_system.dto.DoctorRequestDto;
import com.hospital.hospital_management_system.dto.DoctorResponseDto;
import com.hospital.hospital_management_system.entity.DepartmentEntity;
import com.hospital.hospital_management_system.entity.DoctorEntity;
import com.hospital.hospital_management_system.exception.ResourceNotFoundException;
import com.hospital.hospital_management_system.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;


    // GET all doctors
    public List<DoctorResponseDto> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }


    // GET doctor by ID
    public DoctorResponseDto getDoctorById(Long id) {

        DoctorEntity doctor =
                doctorRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found with id: " + id
                                )
                        );

        return convertToResponseDto(doctor);
    }


    // CREATE doctor
    public DoctorResponseDto createDoctor(
            DoctorRequestDto dto) {

        DoctorEntity doctor = new DoctorEntity();

        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setEmail(dto.getEmail());

        DoctorEntity savedDoctor =
                doctorRepository.save(doctor);

        return convertToResponseDto(savedDoctor);
    }


    // UPDATE doctor
    public DoctorResponseDto updateDoctor(
            Long id,
            DoctorRequestDto dto) {

        DoctorEntity doctor =
                doctorRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found with id: " + id
                                )
                        );

        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setEmail(dto.getEmail());

        DoctorEntity updatedDoctor =
                doctorRepository.save(doctor);

        return convertToResponseDto(updatedDoctor);
    }


    // DELETE doctor
    public void deleteDoctor(Long id) {

        DoctorEntity doctor =
                doctorRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found with id: " + id
                                )
                        );

        doctorRepository.delete(doctor);
    }


    // Entity → Response DTO
    private DoctorResponseDto convertToResponseDto(
            DoctorEntity doctor) {

        Set<Long> departmentIds =
                doctor.getDepartmentEntitySet()
                        .stream()
                        .map(DepartmentEntity::getId)
                        .collect(Collectors.toSet());

        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .email(doctor.getEmail())
                .departmentIds(departmentIds)
                .build();
    }
}