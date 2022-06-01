package com.medicscreen.diabetesmainapp.proxies;

import com.medicscreen.diabetesmainapp.proxies.dto.Patient;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PatientProxy {
  @RequestLine("GET /patients")
  @Headers("Content-Type: application/json")
  List<Patient> getAllPatients();

  @RequestLine("GET /patients/{id}")
  @Headers("Content-Type: application/json")
  Patient getPatientById(@Param int id);

  @RequestLine("POST /patients")
  @Headers("Content-Type: application/json")
  Patient addPatient(@RequestBody Patient patient);

  @RequestLine("PUT /patients/{id}")
  @Headers("Content-Type: application/json")
  Patient updatePatient (@Param int id, @RequestBody Patient patientToUpdate);

  @RequestLine("DELETE /patients/{id}")
  void deletePatient(@Param int id);
}
