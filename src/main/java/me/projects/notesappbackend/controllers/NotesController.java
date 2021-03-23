package me.projects.notesappbackend.controllers;

import me.projects.notesappbackend.models.Note;
import me.projects.notesappbackend.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotesController {
  private final NoteService noteService;

  public NotesController(NoteService noteService) {
    this.noteService = noteService;
  }

  @GetMapping("notes")
  public ResponseEntity<?> getAllNotes() {
    return ResponseEntity.ok(noteService.getAllNotes());
  }

  @PostMapping("save")
  public ResponseEntity<?> saveNote(@RequestBody Note note) {
    return ResponseEntity.ok(noteService.saveNote(note));
  }

  @DeleteMapping("{nid}")
  public void deleteNote(@PathVariable Long nid) {
    noteService.deleteNote(nid);
  }
}
