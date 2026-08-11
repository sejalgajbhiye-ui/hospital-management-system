package com.hospital.hospital_management_system.repository;

import com.hospital.hospital_management_system.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}