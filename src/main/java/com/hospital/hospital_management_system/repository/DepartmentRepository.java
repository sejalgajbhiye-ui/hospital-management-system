package com.hospital.hospital_management_system.repository;

import com.hospital.hospital_management_system.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}