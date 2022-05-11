package com.medicscreen.diabetesmainapp.proxies.dto;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class Patient {
  private Integer id;

  @NotEmpty(message = "Saisissez un prénom obligatoirement")
  private String firstName;

  @NotEmpty(message = "Saisissez un nom obligatoirement")
  private String lastName;

  @NotEmpty(message = "Saisissez une date de naissance obligatoirement (ex: 01-01-1900)")
  private String dateOfBirth;

  @NotEmpty(message = "Sélectionnez un genre obligatoirement")
  private String gender;

  private String address;
  private String phone;
  private List<NoteDto> notes= new ArrayList<>();
  private String diagnostic;

  public Patient() {
  }

  public Patient(PatientBuilder builder){
    this.id= builder.id;
    this.firstName= builder.firstName;
    this.lastName= builder.lastName;
    this.dateOfBirth= builder.dateOfBirth;
    this.gender= builder.gender;
    this.address= builder.address;
    this.phone= builder.phone;
  }

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public String getGender() {
    return gender;
  }

  public String getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }

  public List<NoteDto> getNotes() {
    return notes;
  }

  public String getDiagnostic() {
    return diagnostic;
  }

  public void setDiagnostic(String diagnostic) {
    this.diagnostic = diagnostic;
  }

  public static class PatientBuilder {
    private Integer id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String phone;
    private List<NoteDto>notes=  new ArrayList<>();

    public PatientBuilder id(Integer id){
      this.id=id;
      return this;
    }

    public PatientBuilder firstName(String firstName){
      this.firstName=firstName;
      return this;
    }

    public PatientBuilder lastName(String lastName){
      this.lastName=lastName;
      return this;
    }

    public PatientBuilder dateOfBirth(String dateOfBirth){
      this.dateOfBirth=dateOfBirth;
      return this;
    }

    public PatientBuilder gender(String gender){
      this.gender=gender;
      return this;
    }

    public PatientBuilder address(String address){
      this.address=address;
      return this;
    }

    public PatientBuilder phone(String phone){
      this.phone=phone;
      return this;
    }

    public Patient build(){
      return new Patient(this);
    }
  }
}
