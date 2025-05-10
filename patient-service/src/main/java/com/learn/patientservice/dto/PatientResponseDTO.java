package com.learn.patientservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponseDTO {
    private String id;
    private String patientName;
    private String email;
    private String dateOfBirth;
    private String address;
    private String dateOfAdmission;
}
