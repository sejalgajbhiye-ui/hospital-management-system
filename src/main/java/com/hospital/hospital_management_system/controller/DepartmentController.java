package com.hospital.hospital_management_system.controller;

import com.hospital.hospital_management_system.dto.DepartmentRequestDto;
import com.hospital.hospital_management_system.dto.DepartmentResponseDto;
import com.hospital.hospital_management_system.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // GET all departments
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // GET department by ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    // CREATE department
    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        DepartmentResponseDto createdDepartment = departmentService.createDepartment(departmentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDepartment);
    }

    // UPDATE department
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentRequestDto));
    }

    // DELETE department
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}