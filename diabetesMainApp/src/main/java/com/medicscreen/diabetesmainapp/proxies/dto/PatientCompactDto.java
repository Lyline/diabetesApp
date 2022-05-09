package com.medicscreen.diabetesmainapp.proxies.dto;

import java.time.LocalDate;

public class PatientCompactDto {

  private Integer id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String address;

  public PatientCompactDto() {}

  public PatientCompactDto(PatientCompactBuilder builder){
    this.id= builder.id;
    this.firstName= builder.firstName;
    this.lastName= builder.lastName;
    this.dateOfBirth= builder.dateOfBirth;
    this.address= builder.address;
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

  public String getAddress() {
    return address;
  }

  public static class PatientCompactBuilder{
    private Integer id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;

    public PatientCompactBuilder id (Integer id){
      this.id=id;
      return this;
    }

    public PatientCompactBuilder firstName(String firstName){
      this.firstName=firstName;
      return this;
    }

    public PatientCompactBuilder lastName(String lastName){
      this.lastName=lastName;
      return this;
    }

    public PatientCompactBuilder dateOfBirth(String dateOfBirth){
      this.dateOfBirth=dateOfBirth;
      return this;
    }

    public PatientCompactBuilder address(String address){
      this.address=address;
      return this;
    }

    public PatientCompactDto build(){
      return new PatientCompactDto(this);
    }
  }

}
