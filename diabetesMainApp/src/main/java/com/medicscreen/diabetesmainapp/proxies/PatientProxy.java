package com.medicscreen.diabetesmainapp.proxies;

import com.medicscreen.diabetesmainapp.proxies.dto.Patient;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PatientProxy {
  @RequestLine("GET /patients")
  List<Patient> getAllPatients();

  @RequestLine("GET /patients/{id}")
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
