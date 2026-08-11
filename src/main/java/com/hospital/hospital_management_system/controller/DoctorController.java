package com.hospital.hospital_management_system.controller;

import com.hospital.hospital_management_system.dto.DoctorRequestDto;
import com.hospital.hospital_management_system.dto.DoctorResponseDto;
import com.hospital.hospital_management_system.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // GET all doctors
    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // GET doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    // CREATE doctor
    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody DoctorRequestDto doctorRequestDto) {
        DoctorResponseDto createdDoctor = doctorService.createDoctor(doctorRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDoctor);
    }

    // UPDATE doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorRequestDto doctorRequestDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorRequestDto));
    }

    // DELETE doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}