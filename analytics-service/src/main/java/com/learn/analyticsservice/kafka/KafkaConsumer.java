package com.learn.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event){
        PatientEvent patientEvent = null;
        try {
            patientEvent = PatientEvent.parseFrom(event);
            //perform business logic
            log.info("Received event from kafka: {}",patientEvent.toString());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error while parsing event {}", e.getMessage());
        }
        System.out.println("Received event from kafka: "+patientEvent);
    }
}
