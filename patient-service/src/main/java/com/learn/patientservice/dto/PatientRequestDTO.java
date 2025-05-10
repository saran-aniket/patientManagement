package com.learn.patientservice.dto;

import com.learn.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequestDTO {
    @NotBlank(message = "Please provide a patient name")
    @Size(max = 100, message = "Patient name should be less than 100 characters")
    private String patient_name;

    @NotBlank(message = "Please provide an email address")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Please provide an address")
    @Size(max = 1000, message = "Address should be less than 1000 characters")
    private String address;

    @NotBlank(message = "Please provide a date of birth")
    private String date_of_birth;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Please provide a date of admission")
    private String date_of_admission;
}
