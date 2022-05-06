package com.medicscreen.diabetesmainapp.proxies;

import com.medicscreen.diabetesmainapp.proxies.dto.Patient;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PatientProxy {
  @RequestLine("GET /patients")
  List<Patient> getAllPatients();

  @RequestLine("GET /patients/{id}")
  Patient getPatientById(@Param int id);

 /* @RequestLine("POST /patients")
  Patient addPatient(@Valid @RequestBody Patient patient);

  @RequestLine("PUT /patient/{id}")
  Patient updatePatient (@PathVariable int id, @Valid @RequestBody Patient patientToUpdate);

  @RequestLine("DELETE /patients/{id}")
  void deletePatient(@PathVariable int id);*/

}
