package com.learn.patientservice.service;

import com.learn.patientservice.dto.PatientRequestDTO;
import com.learn.patientservice.dto.PatientResponseDTO;
import com.learn.patientservice.exceptions.DuplicateEmailException;
import com.learn.patientservice.exceptions.PatientDoesNotExistException;
import com.learn.patientservice.grpc.BillingServiceGrpcClient;
import com.learn.patientservice.mapper.PatientMapper;
import com.learn.patientservice.model.Patient;
import com.learn.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getALLPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findByEmail(patientRequestDTO.getEmail());
        if (patient != null) {
            throw new DuplicateEmailException("Email already exists");
        }
        Patient newPatient = PatientMapper.toModel(patientRequestDTO);
        Patient createdPatient = patientRepository.save(newPatient);

        billingServiceGrpcClient.createBillingAccount(createdPatient.getId().toString(), createdPatient.getPatient_name(), createdPatient.getEmail());

        return PatientMapper.toDTO(createdPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findByEmailAndIdNot(patientRequestDTO.getEmail(), id);
        if (patient != null) {
            throw new DuplicateEmailException("Email already exists");
        }
        Patient updatedPatient = patientRepository.findById(id).orElseThrow(() -> new PatientDoesNotExistException("Patient with Id " + id + " does not exists"));
        updatedPatient.setPatient_name(patientRequestDTO.getPatient_name());
        updatedPatient.setEmail(patientRequestDTO.getEmail());
        updatedPatient.setAddress(patientRequestDTO.getAddress());
        updatedPatient.setDate_of_birth(LocalDate.parse(patientRequestDTO.getDate_of_birth()));
        return PatientMapper.toDTO(patientRepository.save(updatedPatient));
    }

    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientDoesNotExistException("Patient " + "with id " + id + " does not exists"));
        patientRepository.deleteById(id);
    }
}
