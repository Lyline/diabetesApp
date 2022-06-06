package com.medicscreen.diabetesmainapp;

import com.medicscreen.diabetesmainapp.exception.HandlerException;
import com.medicscreen.diabetesmainapp.proxies.dto.Note;
import com.medicscreen.diabetesmainapp.proxies.dto.Patient;
import com.medicscreen.diabetesmainapp.proxies.dto.PatientCompactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/diabetesApp")
public class DiabetesController extends HandlerException {

  @Autowired
  private DiabetesService service;

  @GetMapping("/patients")
  public ResponseEntity<List<PatientCompactDto>> getAllPatients(){
    List<PatientCompactDto> patients=service.getAllPatients();

    if (patients.isEmpty()){
      return new ResponseEntity<>(Collections.emptyList(),HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(patients,HttpStatus.OK);
  }

  @GetMapping("/patients/{id}")
  public ResponseEntity<Patient> getPatientById(@PathVariable int id){
    Patient patient= service.getPatientById(id);

    if (Objects.nonNull(patient)) {
      return new ResponseEntity<>(patient,HttpStatus.OK);
    }
    return new ResponseEntity(Collections.emptyList(),HttpStatus.NOT_FOUND);
  }

  @PostMapping("/patients")
  public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient){
    Patient patientSaved= service.addPatient(patient);

    URI location= ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("patients/{id}")
        .buildAndExpand(patientSaved.getId())
        .toUri();

    return ResponseEntity.created(location).body(patientSaved);
  }

  @PutMapping(value = "/patients/{id}")
  public ResponseEntity<Patient> updatePatient(@PathVariable int id, @Valid @RequestBody Patient patientToUpdate){
    Patient patientUpdated= service.updatePatient(id,patientToUpdate);

    URI location= ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("patients/{id}")
        .buildAndExpand(patientUpdated.getId())
        .toUri();

    return ResponseEntity.created(location).body(patientToUpdate);
  }

  @DeleteMapping("/patients/{id}")
  public ResponseEntity deletePatient(@PathVariable int id){
    service.deletePatient(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping("/patients/{patientId}/notes")
  public ResponseEntity<Note> addNote(@PathVariable int patientId, @Valid @RequestBody Note note){

    Note noteSaved= service.addNote(note);

    return new ResponseEntity<>(noteSaved,HttpStatus.CREATED);
  }

  @PutMapping("/patients/{patientId}/notes/{noteId}")
  public ResponseEntity<Note> updateNote(@PathVariable String patientId, @PathVariable String noteId,
                                         @Valid @RequestBody Note noteToUpdate){
    Note noteUpdated= service.updateNote(noteId, noteToUpdate);

    if (Objects.nonNull(noteUpdated)){
      return new ResponseEntity<>(noteUpdated,HttpStatus.CREATED);
    }return null;
  }

  @DeleteMapping("/patients/{patientId}/notes/{noteId}")
  public ResponseEntity deleteNote(@PathVariable String patientId, @PathVariable String noteId){
    boolean patientDeleted= service.deleteNote(noteId);

    if (patientDeleted){
      return new ResponseEntity(HttpStatus.OK);
    }return new ResponseEntity(HttpStatus.NOT_FOUND);
  }
}

