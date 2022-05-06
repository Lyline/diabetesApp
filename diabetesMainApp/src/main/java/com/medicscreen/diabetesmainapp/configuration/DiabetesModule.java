package com.medicscreen.diabetesmainapp.configuration;

import com.medicscreen.diabetesmainapp.proxies.DiagnosticProxy;
import com.medicscreen.diabetesmainapp.proxies.NoteProxy;
import com.medicscreen.diabetesmainapp.proxies.PatientProxy;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiabetesModule {
  Logger logger= LoggerFactory.getLogger(DiabetesModule.class);

  @Value("${patientURL}")
  private String patientURL;

  @Value("${noteURL}")
  private String noteURL;

  @Value("${diagnosticURL}")
  private String diagnosticURL;

  @Bean
  public PatientProxy getPatientService(){
    logger.info("Initializing patientProxy @ "+ patientURL);
    return Feign.builder().client(new OkHttpClient())
        .encoder(new GsonEncoder())
        .decoder(new GsonDecoder())
        .target(PatientProxy.class, patientURL);
  }

  @Bean
  public NoteProxy getNoteService(){
    logger.info("Initializing noteProxy @ "+ noteURL);
    return Feign.builder().client(new OkHttpClient())
        .encoder(new GsonEncoder())
        .decoder(new GsonDecoder())
        .target(NoteProxy.class, noteURL);
  }

  @Bean
  public DiagnosticProxy getDiagnosticService(){
    logger.info("Initializing diagnosticProxy @ " + diagnosticURL);
    return Feign.builder().client(new OkHttpClient())
        .encoder(new GsonEncoder())
        .decoder(new GsonDecoder())
        .target(DiagnosticProxy.class, diagnosticURL);
  }
}
