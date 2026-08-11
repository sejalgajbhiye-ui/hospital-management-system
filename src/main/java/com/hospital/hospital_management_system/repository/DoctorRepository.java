package com.hospital.hospital_management_system.repository;

import com.hospital.hospital_management_system.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}