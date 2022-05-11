package com.medicscreen.diabetesmainapp.patient;

import com.medicscreen.diabetesmainapp.DiabetesService;
import com.medicscreen.diabetesmainapp.proxies.DiagnosticProxy;
import com.medicscreen.diabetesmainapp.proxies.NoteProxy;
import com.medicscreen.diabetesmainapp.proxies.PatientProxy;
import com.medicscreen.diabetesmainapp.proxies.dto.Diagnostic;
import com.medicscreen.diabetesmainapp.proxies.dto.Note;
import com.medicscreen.diabetesmainapp.proxies.dto.Note.NoteBuilder;
import com.medicscreen.diabetesmainapp.proxies.dto.Patient;
import com.medicscreen.diabetesmainapp.proxies.dto.Patient.PatientBuilder;
import com.medicscreen.diabetesmainapp.proxies.dto.PatientCompactDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PatientServiceTest {
  private final PatientProxy patientProxy= mock(PatientProxy.class);
  private final NoteProxy noteProxy= mock(NoteProxy.class);
  private final DiagnosticProxy diagnosticProxy= mock(DiagnosticProxy.class);

  private DiabetesService service=new DiabetesService(patientProxy,noteProxy,diagnosticProxy);

  Patient patient= new PatientBuilder()
      .id(1)
      .firstName("John")
      .lastName("Doe")
      .dateOfBirth("2000-01-01")
      .gender("M")
      .address("Address of John")
      .phone("123-453")
      .build();

  Patient patient1= new PatientBuilder()
      .firstName("Jane")
      .lastName("Doe")
      .dateOfBirth("1990-01-01")
      .gender("F")
      .address("Address of Jane")
      .phone("123-123")
      .build();

  Note note= new NoteBuilder()
      .id("aze")
      .patientId(1)
      .noteContent("test note 1")
      .build();

  Note note1= new NoteBuilder()
      .id("ert")
      .patientId(1)
      .noteContent("test note 2")
      .build();

  @Test
  void givenTwoPatientWhenGetAllPatientThenReturnListOfTwoPatients() {
    //Given
    when(patientProxy.getAllPatients()).thenReturn(List.of(patient,patient1));

    //When
    List<PatientCompactDto> actual= service.getAllPatients();

    //Then
    assertThat(actual.size()).isEqualTo(2);
    verify(patientProxy, times(1)).getAllPatients();
  }

  @Test
  void givenNoPatientWhenGetAllPatientThenReturnEmptyList() {
    //Given
    when(patientProxy.getAllPatients()).thenReturn(Collections.emptyList());

    //When
    List<PatientCompactDto> actual= service.getAllPatients();

    //Then
    assertTrue(actual.isEmpty());
    verify(patientProxy, times(1)).getAllPatients();
  }

  @Test
  void givenPatientExistingWhenGetPatientByIdThenReturnPatient() {
    //Given
    Diagnostic diagnostic=new Diagnostic("Borderline");

    when(patientProxy.getPatientById(anyInt())).thenReturn(patient);
    when(noteProxy.getAllNotesByPatient(anyInt())).thenReturn(List.of(note,note1));
    when(diagnosticProxy.getDiagnostic(anyInt())).thenReturn(diagnostic);

    //When
    Patient actual= service.getPatientById(1);

    //Then
    assertThat(actual.getFirstName()).isEqualTo("John");
    assertThat(actual.getNotes().size()).isEqualTo(2);
    verify(patientProxy, times(1)).getPatientById(1);
    verify(noteProxy, times(1)).getAllNotesByPatient(1);
    verify(diagnosticProxy, times(1)).getDiagnostic(1);
  }

  @Test
  void givenPatientNotExistingWhenGetPatientByIdThenReturnNull() {
    //Given
    when(patientProxy.getPatientById(anyInt())).thenReturn(null);

    //When
    Patient actual= service.getPatientById(1);

    //Then
    assertNull(actual);
    verify(patientProxy, times(1)).getPatientById(1);
    verify(noteProxy, times(0)).getAllNotesByPatient(1);
    verify(diagnosticProxy,times(0)).getDiagnostic(1);
  }

  @Test
  void givenNewValidPatientWhenAddPatientThenReturnPatientSaved() {
    //Given
    Patient patientToSave= new PatientBuilder()
        .firstName("John")
        .lastName("Doe")
        .dateOfBirth("2000-01-01")
        .gender("M")
        .address("Address of John")
        .phone("123-453")
        .build();

    when(patientProxy.addPatient(any())).thenReturn(patient);

    //When
    Patient actual= service.addPatient(patientToSave);

    //Then
    assertThat(actual).isEqualTo(patient);
    verify(patientProxy, times(1)).addPatient(patientToSave);
  }

  @Test
  void givenNewNotValidPatientWhenAddPatientThenReturnNull() {
    //Given
    Patient patientToSave= new PatientBuilder()
        .firstName("John")
        .lastName("Doe")
        .dateOfBirth("2000-01-01")
        .gender("M")
        .address("Address of John")
        .phone("123-453")
        .build();

    when(patientProxy.addPatient(any())).thenReturn(null);

    //When
    Patient actual= service.addPatient(patientToSave);

    //Then
    assertNull(actual);
    verify(patientProxy,times(1)).addPatient(patientToSave);
  }

  @Test
  void givenPatientExistingWithValidUpdateWhenUpdatePatientThenReturnPatient() {
    //Given
    Patient patientUpdated= new PatientBuilder()
        .id(1)
        .firstName("Mary")
        .lastName("Smith")
        .dateOfBirth("1990-01-01")
        .gender("F")
        .address("Address of Mary")
        .phone("000-000")
        .build();

    when(patientProxy.updatePatient(anyInt(),any())).thenReturn(patientUpdated);

    //When
    Patient actual= service.updatePatient(1,patient);

    //Then
    assertSame(actual,patientUpdated);
  }

  @Test
  void givenPatientExistingWhenDeletePatientThenPatientDeleted() {
    //Given
    //When
    service.deletePatient(1);

    //Then
    verify(patientProxy,times(1)).deletePatient(1);
  }
}
