package com.hospital.hospital_management_system.controller;

import com.hospital.hospital_management_system.dto.AssignInsuranceRequestDto;
import com.hospital.hospital_management_system.dto.PatientRequestDto;
import com.hospital.hospital_management_system.dto.PatientResponseDto;
import com.hospital.hospital_management_system.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET all patients
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // GET patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // CREATE patient
    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto) {
        PatientResponseDto savedPatient = patientService.createPatient(patientRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPatient);
    }

    // UPDATE patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDto));
    }

    // DELETE patient
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

    // ASSIGN insurance to patient
    @PutMapping("/{pid}/insurance")
    public ResponseEntity<PatientResponseDto> assignInsuranceToPatient(@PathVariable Long pid, @Valid @RequestBody AssignInsuranceRequestDto requestDto) {
        return ResponseEntity.ok(patientService.assignInsuranceToPatient(pid, requestDto));
    }

    // DISSOCIATE insurance from patient
    @DeleteMapping("/{pid}/insurance")
    public ResponseEntity<PatientResponseDto> dissociateInsuranceFromPatient(@PathVariable Long pid) {
        return ResponseEntity.ok(patientService.dissociateInsuranceFromPatient(pid));
    }
}