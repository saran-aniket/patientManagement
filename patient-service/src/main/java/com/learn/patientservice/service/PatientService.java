package com.learn.patientservice.service;

import com.learn.patientservice.dto.PatientRequestDTO;
import com.learn.patientservice.dto.PatientResponseDTO;
import com.learn.patientservice.exceptions.DuplicateEmailException;
import com.learn.patientservice.exceptions.PatientDoesNotExistException;
import com.learn.patientservice.mapper.PatientMapper;
import com.learn.patientservice.model.Patient;
import com.learn.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getALLPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findByEmail(patientRequestDTO.getEmail());
        if(patient != null){
            throw new DuplicateEmailException("Email already exists");
        }
        Patient newPatient = PatientMapper.toModel(patientRequestDTO);
        return PatientMapper.toDTO(patientRepository.save(newPatient));
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findByEmailAndIdNot(patientRequestDTO.getEmail(), id);
        if(patient != null){
            throw new DuplicateEmailException("Email already exists");
        }
        Patient updatedPatient = patientRepository.findById(id).orElseThrow(() -> new PatientDoesNotExistException(
                "Patient with Id "+ id + " does not exists"));
        updatedPatient.setPatient_name(patientRequestDTO.getPatient_name());
        updatedPatient.setEmail(patientRequestDTO.getEmail());
        updatedPatient.setAddress(patientRequestDTO.getAddress());
        updatedPatient.setDate_of_birth(LocalDate.parse(patientRequestDTO.getDate_of_birth()));
        return PatientMapper.toDTO(patientRepository.save(updatedPatient));
    }
}
