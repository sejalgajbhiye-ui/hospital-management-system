package com.hospital.hospital_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(name="patient_id",nullable = false) // owning side
    @ToString.Exclude
    private PatientEntity patientEntity;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false) // owning side
    private DoctorEntity doctorEntity;
}
