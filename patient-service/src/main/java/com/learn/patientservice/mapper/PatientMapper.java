package com.learn.patientservice.mapper;

import com.learn.patientservice.dto.PatientRequestDTO;
import com.learn.patientservice.dto.PatientResponseDTO;
import com.learn.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setPatientName(patient.getPatient_name());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setDateOfBirth(patient.getDate_of_birth().toString());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfAdmission(patient.getDate_of_admission().toString());
        return patientResponseDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setPatient_name(patientRequestDTO.getPatient_name());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDate_of_birth(LocalDate.parse(patientRequestDTO.getDate_of_birth()));
        patient.setDate_of_admission(LocalDate.parse(patientRequestDTO.getDate_of_admission()));
        return patient;
    }
}
