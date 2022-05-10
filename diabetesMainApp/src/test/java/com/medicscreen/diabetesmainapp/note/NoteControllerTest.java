package com.medicscreen.diabetesmainapp.note;

import com.medicscreen.diabetesmainapp.DiabetesController;
import com.medicscreen.diabetesmainapp.DiabetesService;
import com.medicscreen.diabetesmainapp.proxies.dto.Note;
import com.medicscreen.diabetesmainapp.proxies.dto.Note.NoteBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiabetesController.class)
public class NoteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DiabetesService service;

  Note note= new NoteBuilder()
      .id("1")
      .patientId(1)
      .noteContent("note of patient 1")
      .build();

  Note note1= new NoteBuilder()
      .id("2")
      .patientId(1)
      .noteContent("second note of patient 1")
      .build();

  @Test
  void givenNewValidNoteForPatientWhenAddNoteThenReturnNoteSavedWithStatus201() throws Exception {
    //Given
    when(service.addNote(any())).thenReturn(note);

    //When
    mockMvc.perform(post(("/diabetesApp/patients/1/notes"))
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"patientId\":1,\"noteContent\":\"note of patient 1\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().json(
            "{\"id\":\"1\"," +
                "\"patientId\":1," +
                "\"noteContent\":\"note of patient 1\"}"
        ));
    //Then
  }

  @Test
  void givenNewNotValidNoteForPatientWhenAddNoteThenReturnWithStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(post(("/diabetesApp/patients/1/notes"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"patientId\":null,\"noteContent\":\"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(
            "{\"noteContent\":\"Le content ne doit pas rester vide\"," +
                "\"patientId\":\"must not be null\"}"
        ));
  }

  @Test
  void givenNoteExistingWithValidUpdateForPatientWhenUpdateNoteThenReturnNoteUpdatedWithStatus201() throws Exception {
    //Given
    Note note= new NoteBuilder()
        .id("2")
        .patientId(1)
        .noteContent("note updated")
        .build();

    when(service.updateNote(anyString(),any())).thenReturn(note);

    //When
    mockMvc.perform(put("/diabetesApp/patients/1/notes/2")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"patientId\":1,\"noteContent\":\"note updated\"}"))

        .andExpect(status().isCreated())
        .andExpect(content().json(
            "{\"patientId\":1,\"noteContent\":\"note updated\"}"
        ));
  }

  @Test
  void givenNoteExistingWithNotValidUpdateForPatientWhenUpdateNoteThenReturnErrorMessagesWithStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(put("/diabetesApp/patients/1/notes/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"patientId\":null,\"noteContent\":\"\"}"))

        .andExpect(status().isBadRequest())
        .andExpect(content().json(
            "{\"noteContent\":\"Le content ne doit pas rester vide\",\"patientId\":\"must not be null\"}"
        ));
  }

  @Test
  void givenNoteExistingForPatientWhenDeleteNoteThenNoteDeletedWithStatus200() throws Exception {
    //Given
    when(service.deleteNote(anyString())).thenReturn(true);

    //When
    mockMvc.perform(delete("/diabetesApp/patients/1/notes/1"))
        .andExpect(status().isOk());
  }

  @Test
  void givenNoteNotExistingForPatientWhenDeleteNoteThenWithStatus404() throws Exception {
    //Given
    when(service.deleteNote(anyString())).thenReturn(false);

    //When
    mockMvc.perform(delete("/diabetesApp/patients/1/notes/1"))
        .andExpect(status().isNotFound());
  }
}
