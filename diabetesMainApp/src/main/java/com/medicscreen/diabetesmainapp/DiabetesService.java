package com.medicscreen.diabetesmainapp;

import com.medicscreen.diabetesmainapp.proxies.DiagnosticProxy;
import com.medicscreen.diabetesmainapp.proxies.NoteProxy;
import com.medicscreen.diabetesmainapp.proxies.PatientProxy;
import com.medicscreen.diabetesmainapp.proxies.dto.Note;
import com.medicscreen.diabetesmainapp.proxies.dto.NoteDto;
import com.medicscreen.diabetesmainapp.proxies.dto.Patient;
import com.medicscreen.diabetesmainapp.proxies.dto.PatientCompactDto;
import com.medicscreen.diabetesmainapp.proxies.dto.PatientCompactDto.PatientCompactBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DiabetesService {

  private final PatientProxy patientProxy;
  private final NoteProxy noteProxy;
  private final DiagnosticProxy diagnosticProxy;

  public DiabetesService(PatientProxy patientProxy, NoteProxy noteProxy, DiagnosticProxy diagnosticProxy) {
    this.patientProxy = patientProxy;
    this.noteProxy = noteProxy;
    this.diagnosticProxy = diagnosticProxy;
  }

  public List<PatientCompactDto> getAllPatients(){
    List<PatientCompactDto> patients= new ArrayList<>();
    List<Patient> patientDtos= patientProxy.getAllPatients();

    for (Patient patient:patientDtos){
      PatientCompactDto patientCompact= new PatientCompactBuilder()
          .id(patient.getId())
          .firstName(patient.getFirstName())
          .lastName(patient.getLastName())
          .dateOfBirth(patient.getDateOfBirth())
          .address(patient.getAddress())
          .build();

      patients.add(patientCompact);
    }
    return patients;
  }

  public Patient getPatientById(int id) {
    Patient patient=patientProxy.getPatientById(id);
    List<Note> notes= noteProxy.getAllNotesByPatient(id);

    if (Objects.nonNull(notes)) {
      for (Note note:notes){
        NoteDto noteDto= new NoteDto(note.getId(),note.getNoteContent());
        patient.getNotes().add(noteDto);
      }
    }
    return patient;
  }

  public Patient addPatient(Patient patient) {
    return patientProxy.addPatient(patient);
  }

  public Patient updatePatient(int id, Patient patientToUpdate) {
    return patientProxy.updatePatient(id,patientToUpdate);
  }

  public void deletePatient(int id) {
    patientProxy.deletePatient(id);
  }

  public Note addNote(Note note) {
    return noteProxy.addNote(note);
  }

  public Note updateNote(String noteId, Note noteToUpdate) {
    return noteProxy.updateNote(noteId,noteToUpdate);
  }

  public boolean deleteNote(String noteId) {
    return noteProxy.deleteNoteById(noteId);
  }
}
