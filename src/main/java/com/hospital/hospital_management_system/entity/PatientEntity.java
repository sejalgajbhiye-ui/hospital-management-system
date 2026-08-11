package com.hospital.hospital_management_system.entity;

import com.hospital.hospital_management_system.entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(
//        name = "patient_tbl",
        uniqueConstraints = {
                @UniqueConstraint(name="unique_patient_email",columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_birthdate",columnNames = {"name","birthDate"})
        },
        indexes = {
                @Index(name = "idx_patient_birthdate",columnList = "birthDate")
        }
)
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 40)
    private String name;

    private LocalDate birthDate;

    @ToString.Exclude
    @Column(unique = true,nullable = false)
    private String email;

    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    public BloodGroupType bloodGroup;

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true) // patient rahnar, insurance delete honar
    @JoinColumn(name = "patient_insurance_id") // owning side
    private InsuranceEntity insuranceEntity;

    @OneToMany(mappedBy = "patientEntity",cascade = {CascadeType.REMOVE},orphanRemoval = true,fetch = FetchType.EAGER) // inverse side
    private List<AppointmentEntity> appointments=new ArrayList<>();
}
