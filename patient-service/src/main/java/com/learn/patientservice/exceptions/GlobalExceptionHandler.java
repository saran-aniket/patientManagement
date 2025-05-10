package com.learn.patientservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String,String>> handleDuplicateEmailException(DuplicateEmailException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(PatientDoesNotExistException.class)
    public ResponseEntity<Map<String,String>> handlePatientDoesNotExistException(PatientDoesNotExistException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.badRequest().body(errorMap);
    }
}
