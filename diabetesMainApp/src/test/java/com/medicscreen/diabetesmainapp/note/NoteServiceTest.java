package com.medicscreen.diabetesmainapp.note;

import com.medicscreen.diabetesmainapp.DiabetesService;
import com.medicscreen.diabetesmainapp.proxies.DiagnosticProxy;
import com.medicscreen.diabetesmainapp.proxies.NoteProxy;
import com.medicscreen.diabetesmainapp.proxies.PatientProxy;
import com.medicscreen.diabetesmainapp.proxies.dto.Note;
import com.medicscreen.diabetesmainapp.proxies.dto.Note.NoteBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

  private final PatientProxy patientProxy= mock(PatientProxy.class);
  private final NoteProxy noteProxy= mock(NoteProxy.class);
  private final DiagnosticProxy diagnosticProxy= mock(DiagnosticProxy.class);

  private DiabetesService service=new DiabetesService(patientProxy,noteProxy,diagnosticProxy);

  Note note= new NoteBuilder()
      .id("1")
      .patientId(1)
      .noteContent("Note of patient 1")
      .build();

  Note note1= new NoteBuilder()
      .id("2")
      .patientId(1)
      .noteContent("second note of patient 1")
      .build();


  @Test
  void givenNewValidNoteWhenAddNoteThenReturnNoteSaved() {
    //Given
    Note noteToSave= new NoteBuilder()
        .patientId(1)
        .noteContent("Note of patient 1")
        .build();

    when(noteProxy.addNote(any())).thenReturn(note);

    //When
    Note actual= service.addNote(noteToSave);

    //Then
    assertSame(actual,note);
    verify(noteProxy, times(1)).addNote(noteToSave);
  }

  @Test
  void givenNoteExistingWithUpdateNoteWhenUpdateNoteThenNoteUpdated() {
    //Given
    Note noteToUpdate= new NoteBuilder()
        .patientId(1)
        .noteContent("second note of patient 1")
        .build();

    when(noteProxy.updateNote(anyString(),any())).thenReturn(note1);

    //When
    Note actual= service.updateNote("1",noteToUpdate);

    //Then
    assertSame(actual,note1);
    verify(noteProxy, times(1)).updateNote("1",noteToUpdate);
  }

  @Test
  void givenNoteExistingWithDeleteNoteThenReturnTrue() {
    //Given
    when(noteProxy.deleteNoteById(anyString())).thenReturn(true);

    //When
    boolean actual= service.deleteNote("1");

    //Then
    assertTrue(actual);
    verify(noteProxy, times(1)).deleteNoteById("1");
  }

  @Test
  void givenNoteNotExistingWithDeleteNoteThenReturnFalse() {
    //Given
    when(noteProxy.deleteNoteById(anyString())).thenReturn(false);

    //When
    boolean actual= service.deleteNote("1");

    //Then
    assertFalse(actual);
    verify(noteProxy, times(1)).deleteNoteById("1");
  }
}
