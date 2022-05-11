package com.medicscreen.diabetesmainapp.proxies;

import com.medicscreen.diabetesmainapp.proxies.dto.Diagnostic;
import feign.Param;
import feign.RequestLine;

public interface DiagnosticProxy {

  @RequestLine("GET /{patientId}")
  Diagnostic getDiagnostic(@Param int patientId);
}
