package com.hospital.hospital_management_system.service;

import com.hospital.hospital_management_system.dto.AppointmentRequestDto;
import com.hospital.hospital_management_system.dto.AppointmentResponseDto;
import com.hospital.hospital_management_system.entity.AppointmentEntity;
import com.hospital.hospital_management_system.entity.DoctorEntity;
import com.hospital.hospital_management_system.entity.PatientEntity;
import com.hospital.hospital_management_system.exception.ResourceNotFoundException;
import com.hospital.hospital_management_system.repository.AppointmentRepository;
import com.hospital.hospital_management_system.repository.DoctorRepository;
import com.hospital.hospital_management_system.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;


    // GET all appointments
    public List<AppointmentResponseDto> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }


    // GET appointment by ID
    public AppointmentResponseDto getAppointmentById(Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return convertToResponseDto(appointment);
    }


    // CREATE appointment
    @Transactional
    public AppointmentResponseDto createAppointment(
            AppointmentRequestDto dto) {

        DoctorEntity doctor =
                doctorRepository.findById(dto.getDoctorId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found with id: "
                                                + dto.getDoctorId()
                                )
                        );

        PatientEntity patient =
                patientRepository.findById(dto.getPatientId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id: "
                                                + dto.getPatientId()
                                )
                        );

        AppointmentEntity appointment =
                new AppointmentEntity();

        appointment.setAppointmentTime(
                dto.getAppointmentTime()
        );

        appointment.setReason(
                dto.getReason()
        );

        // Set relationships
        appointment.setDoctorEntity(doctor);
        appointment.setPatientEntity(patient);

        // Maintain bidirectional relationship
        patient.getAppointments().add(appointment);

        AppointmentEntity savedAppointment =
                appointmentRepository.save(appointment);

        return convertToResponseDto(savedAppointment);
    }


    // UPDATE appointment
    @Transactional
    public AppointmentResponseDto updateAppointment(
            Long id,
            AppointmentRequestDto dto) {

        AppointmentEntity appointment =
                appointmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found with id: "
                                                + id
                                )
                        );

        DoctorEntity doctor =
                doctorRepository.findById(dto.getDoctorId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found with id: "
                                                + dto.getDoctorId()
                                )
                        );

        PatientEntity newPatient =
                patientRepository.findById(dto.getPatientId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id: "
                                                + dto.getPatientId()
                                )
                        );

        // Old patient
        PatientEntity oldPatient =
                appointment.getPatientEntity();

        // If patient is changed, remove appointment
        // from old patient's appointment list
        if (oldPatient != null &&
                !oldPatient.getId()
                        .equals(newPatient.getId())) {

            oldPatient.getAppointments()
                    .remove(appointment);
        }

        appointment.setAppointmentTime(
                dto.getAppointmentTime()
        );

        appointment.setReason(
                dto.getReason()
        );

        appointment.setDoctorEntity(doctor);
        appointment.setPatientEntity(newPatient);

        // Add appointment to new patient's list
        if (!newPatient.getAppointments()
                .contains(appointment)) {

            newPatient.getAppointments()
                    .add(appointment);
        }

        AppointmentEntity updatedAppointment =
                appointmentRepository.save(appointment);

        return convertToResponseDto(updatedAppointment);
    }


    // DELETE appointment
    @Transactional
    public void deleteAppointment(Long id) {

        AppointmentEntity appointment =
                appointmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found with id: "
                                                + id
                                )
                        );

        PatientEntity patient =
                appointment.getPatientEntity();

        // Maintain bidirectional relationship
        if (patient != null) {

            patient.getAppointments()
                    .remove(appointment);
        }

        appointmentRepository.delete(appointment);
    }


    // Entity → Response DTO
    private AppointmentResponseDto convertToResponseDto(
            AppointmentEntity appointment) {

        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .appointmentTime(
                        appointment.getAppointmentTime()
                )
                .reason(
                        appointment.getReason()
                )
                .doctorId(
                        appointment.getDoctorEntity()
                                .getId()
                )
                .patientId(
                        appointment.getPatientEntity()
                                .getId()
                )
                .build();
    }
}