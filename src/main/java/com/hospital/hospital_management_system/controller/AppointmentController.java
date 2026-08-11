package com.hospital.hospital_management_system.controller;

import com.hospital.hospital_management_system.dto.AppointmentRequestDto;
import com.hospital.hospital_management_system.dto.AppointmentResponseDto;
import com.hospital.hospital_management_system.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // GET all appointments
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }


    // GET appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    // CREATE appointment
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        AppointmentResponseDto createdAppointment = appointmentService.createAppointment(appointmentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAppointment);
    }

    // UPDATE appointment
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentRequestDto));
    }

    // DELETE appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}