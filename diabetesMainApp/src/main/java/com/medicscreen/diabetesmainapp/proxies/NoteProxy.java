package com.medicscreen.diabetesmainapp.proxies;

import com.medicscreen.diabetesmainapp.proxies.dto.Note;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface NoteProxy {
  @RequestLine("GET /patient_notes/{id}")
  List<Note> getAllNotesByPatient(@Param int id);

  @RequestLine("POST /notes")
  @Headers("Content-Type: application/json")
  Note addNote(Note note);

  @RequestLine("PUT /notes/{noteId}")
  @Headers("Content-Type: application/json")
  Note updateNote(@Param String noteId, Note noteToUpdate);

  @RequestLine("DELETE /notes/{noteId}")
  boolean deleteNoteById(@Param String noteId);
}
