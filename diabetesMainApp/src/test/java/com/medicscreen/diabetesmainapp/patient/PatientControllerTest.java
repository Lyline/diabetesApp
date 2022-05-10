package com.medicscreen.diabetesmainapp.patient;

import com.medicscreen.diabetesmainapp.DiabetesController;
import com.medicscreen.diabetesmainapp.DiabetesService;
import com.medicscreen.diabetesmainapp.proxies.dto.Patient;
import com.medicscreen.diabetesmainapp.proxies.dto.Patient.PatientBuilder;
import com.medicscreen.diabetesmainapp.proxies.dto.PatientCompactDto;
import com.medicscreen.diabetesmainapp.proxies.dto.PatientCompactDto.PatientCompactBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiabetesController.class)
public class PatientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DiabetesService service;

  Patient patient= new PatientBuilder()
      .id(1).firstName("John")
      .lastName("Doe")
      .dateOfBirth("2000-01-01")
      .gender("M").address("Address of John")
      .phone("123-453")
      .build();

  @Test
  void givenTwoPatientsWhenGetAllPatientThenReturnListOfTwoPatientsWithStatus200() throws Exception {
    //Given
    PatientCompactDto patient= new PatientCompactBuilder()
        .firstName("John")
        .lastName("Doe")
        .dateOfBirth("2000-01-01")
        .address("Address of John")
        .build();

    PatientCompactDto patient1= new PatientCompactBuilder()
        .firstName("Jane")
        .lastName("Doe")
        .dateOfBirth("1990-01-01")
        .address("Address of Jane")
        .build();

    when(service.getAllPatients()).thenReturn(List.of(patient,patient1));

    //When
    mockMvc.perform(get("/diabetesApp/patients"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"id\":null," +
            "\"firstName\":\"John\"," +
            "\"lastName\":\"Doe\"," +
            "\"dateOfBirth\":\"2000-01-01\"," +
            "\"address\":\"Address of John\"}," +

            "{\"id\":null," +
            "\"firstName\":\"Jane\"," +
            "\"lastName\":\"Doe\"," +
            "\"dateOfBirth\":\"1990-01-01\"," +
            "\"address\":\"Address of Jane\"}]"
        ));
  }

  @Test
  void givenPatientExistingWhenGetPatientByIdThenReturnPatientWithStatus200() throws Exception {
    //Given
    when(service.getPatientById(anyInt())).thenReturn(patient);

    //When
    mockMvc.perform(get("/diabetesApp/patients/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{\"id\":1," +
                "\"firstName\":\"John\"," +
                "\"lastName\":\"Doe\"," +
                "\"dateOfBirth\":\"2000-01-01\"," +
                "\"gender\":\"M\"," +
                "\"address\":\"Address of John\"," +
                "\"phone\":\"123-453\"," +
                "\"notes\":[]}"
        ));
  }

  @Test
  void givenPatientNotExistingWhenGetPatientByIdThenReturnStatus404() throws Exception {
    //Given
    when(service.getPatientById(anyInt())).thenReturn(null);

    //When
    mockMvc.perform(get("/diabetesApp/patients/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenNewValidPatientWhenAddPatientThenReturnNewPatientWithStatus201() throws Exception {
    //Given
    when(service.addPatient(any())).thenReturn(patient);

    //When
    mockMvc.perform(post("/diabetesApp/patients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            "{\"firstName\":\"John\"," +
                "\"lastName\":\"Doe\"," +
                "\"dateOfBirth\":\"2000-01-01\"," +
                "\"gender\":\"M\"," +
                "\"address\":\"Address of John\"," +
                "\"phone\":\"123-234\"}"
        ))
        .andExpect(status().isCreated());
  }

  @Test
  void givenNewNotValidPatientWhenAddPatientThenReturnErrorMessagesWithStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/diabetesApp/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"firstName\":\"\"," +
                    "\"lastName\":\"\"," +
                    "\"dateOfBirth\":\"\"," +
                    "\"gender\":\"\"," +
                    "\"address\":\"Address of John\"," +
                    "\"phone\":\"123-234\"}"
            ))
        .andExpect(status().isBadRequest())
        .andExpect(content().json(
            "{\"firstName\":\"Saisissez un prénom obligatoirement\"," +
                "\"lastName\":\"Saisissez un nom obligatoirement\"," +
                "\"gender\":\"Sélectionnez un genre obligatoirement\"," +
                "\"dateOfBirth\":\"Saisissez une date de naissance obligatoirement (ex: 01-01-1900)\"" +
                "}"
        ));
  }

  @Test
  void givenPatientExistingWithValidUpdateWhenUpdatePatientThenReturnPatientUpdatedWithStatus201() throws Exception {
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

    when(service.updatePatient(anyInt(),any())).thenReturn(patientUpdated);

    //When
    mockMvc.perform(put("/diabetesApp/patients/1")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"firstName\":\"Mary\"," +
                  "\"lastName\":\"Smith\"," +
                  "\"dateOfBirth\":\"1990-01-01\"," +
                  "\"gender\":\"F\"," +
                  "\"address\":\"Address of Mary\"," +
                  "\"phone\":\"000-000\"}")
          )
        .andExpect(status().isCreated());
  }

  @Test
  void givenPatientExistingWithNotValidUpdateWhenUpdatePatientThenReturnErrorMessagesWithStatus400() throws Exception {
    //Given
    when(service.updatePatient(anyInt(),any())).thenReturn(null);

    //When
    mockMvc.perform(put("/diabetesApp/patients/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"\"," +
                "\"lastName\":\"\"," +
                "\"gender\":\"\"," +
                "\"dateOfBirth\":\"\"}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().json(
            "{\"lastName\":\"Saisissez un nom obligatoirement\"," +
                "\"firstName\":\"Saisissez un prénom obligatoirement\"," +
                "\"gender\":\"Sélectionnez un genre obligatoirement\"," +
                "\"dateOfBirth\":\"Saisissez une date de naissance obligatoirement (ex: 01-01-1900)\"}"
        ));
  }

  @Test
  void givenPatientExistingWhenDeletePatientByIdThenPatientIsDeletedAndReturnStatus200() throws Exception {
    //Given
    //When
    mockMvc.perform(delete("/diabetesApp/patients/1"))
        .andExpect(status().isOk());

    verify(service,times(1)).deletePatient(1);
  }
}
