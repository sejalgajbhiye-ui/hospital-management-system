package com.hospital.hospital_management_system.repository;

import com.hospital.hospital_management_system.entity.InsuranceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<InsuranceEntity, Long> {
}