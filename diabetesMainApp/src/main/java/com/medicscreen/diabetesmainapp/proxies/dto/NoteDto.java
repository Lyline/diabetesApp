package com.medicscreen.diabetesmainapp.proxies.dto;

public class NoteDto {

  private String id;
  private String noteContent;

  public NoteDto() {
  }

  public NoteDto(String id, String noteContent) {
    this.id = id;
    this.noteContent = noteContent;
  }

  public String getId() {
    return id;
  }

  public String getNoteContent() {
    return noteContent;
  }
}
