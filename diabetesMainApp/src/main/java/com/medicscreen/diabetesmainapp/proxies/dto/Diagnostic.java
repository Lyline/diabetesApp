package com.medicscreen.diabetesmainapp.proxies.dto;

public class Diagnostic {

  private String diagnostic;
  private int age;

  public Diagnostic(){}

  public Diagnostic(int age, String diagnostic) {
    this.age= age;
    this.diagnostic = diagnostic;
  }

  public String getDiagnostic() {
    return diagnostic;
  }

  public int getAge() {
    return age;
  }
}
