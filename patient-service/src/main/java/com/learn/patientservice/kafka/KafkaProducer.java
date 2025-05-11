package com.learn.patientservice.kafka;

import com.learn.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent patientEvent = PatientEvent.newBuilder().setPatientId(patient.getId().toString())
                .setEmail(patient.getEmail())
                .setName(patient.getPatient_name())
                .setEventType("PATIENT_CREATED")
                .build();
        try{
            kafkaTemplate.send("patient",patientEvent.toByteArray());
        }catch (Exception e){
            log.error("Error while sending patient event to kafka {}",patientEvent);
        }
    }
}
