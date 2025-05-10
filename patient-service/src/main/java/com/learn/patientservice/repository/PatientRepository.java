package com.learn.patientservice.repository;

import com.learn.patientservice.model.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Patient findByEmail(@NotBlank @Email String email);
    Patient findByEmailAndIdNot(String email, UUID id);
}
