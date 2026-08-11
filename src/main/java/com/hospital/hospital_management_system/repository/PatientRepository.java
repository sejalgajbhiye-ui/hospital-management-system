package com.hospital.hospital_management_system.repository;

import com.hospital.hospital_management_system.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}