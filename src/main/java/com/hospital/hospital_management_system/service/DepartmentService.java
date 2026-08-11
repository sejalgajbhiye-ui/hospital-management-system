package com.hospital.hospital_management_system.service;

import com.hospital.hospital_management_system.dto.DepartmentRequestDto;
import com.hospital.hospital_management_system.dto.DepartmentResponseDto;
import com.hospital.hospital_management_system.entity.DepartmentEntity;
import com.hospital.hospital_management_system.entity.DoctorEntity;
import com.hospital.hospital_management_system.exception.ResourceNotFoundException;
import com.hospital.hospital_management_system.repository.DepartmentRepository;
import com.hospital.hospital_management_system.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;


    // GET all departments
    public List<DepartmentResponseDto> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }


    // GET department by ID
    public DepartmentResponseDto getDepartmentById(Long id) {

        DepartmentEntity department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id
                                )
                        );

        return convertToResponseDto(department);
    }


    // CREATE department
    public DepartmentResponseDto createDepartment(
            DepartmentRequestDto dto) {

        DepartmentEntity department =
                new DepartmentEntity();

        department.setName(dto.getName());

        // Add doctors if doctor IDs are provided
        if (dto.getDoctorIds() != null &&
                !dto.getDoctorIds().isEmpty()) {

            Set<DoctorEntity> doctors =
                    new HashSet<>();

            for (Long doctorId : dto.getDoctorIds()) {

                DoctorEntity doctor =
                        doctorRepository.findById(doctorId)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "Doctor not found with id: "
                                                        + doctorId
                                        )
                                );

                doctors.add(doctor);
            }

            department.setDoctors(doctors);
        }

        DepartmentEntity savedDepartment =
                departmentRepository.save(department);

        return convertToResponseDto(savedDepartment);
    }


    // UPDATE department
    public DepartmentResponseDto updateDepartment(
            Long id,
            DepartmentRequestDto dto) {

        DepartmentEntity department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: "
                                                + id
                                )
                        );

        department.setName(dto.getName());

        // Update doctors
        Set<DoctorEntity> doctors =
                new HashSet<>();

        if (dto.getDoctorIds() != null) {

            for (Long doctorId : dto.getDoctorIds()) {

                DoctorEntity doctor =
                        doctorRepository.findById(doctorId)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "Doctor not found with id: "
                                                        + doctorId
                                        )
                                );

                doctors.add(doctor);
            }
        }

        department.setDoctors(doctors);

        DepartmentEntity updatedDepartment =
                departmentRepository.save(department);

        return convertToResponseDto(updatedDepartment);
    }


    // DELETE department
    public void deleteDepartment(Long id) {

        DepartmentEntity department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: "
                                                + id
                                )
                        );

        departmentRepository.delete(department);
    }


    // Entity → Response DTO
    private DepartmentResponseDto convertToResponseDto(
            DepartmentEntity department) {

        Set<Long> doctorIds =
                department.getDoctors()
                        .stream()
                        .map(DoctorEntity::getId)
                        .collect(Collectors.toSet());

        return DepartmentResponseDto.builder()
                .id(department.getId())
                .name(department.getName())
                .doctorIds(doctorIds)
                .build();
    }
}