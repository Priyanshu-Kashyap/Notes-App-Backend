package me.projects.notesappbackend.services;

import me.projects.notesappbackend.models.Note;
import me.projects.notesappbackend.repositories.NotesRepository;
import me.projects.notesappbackend.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteService {
  private final NotesRepository notesRepository;
  private final UserRepository userRepository;

  public NoteService(NotesRepository notesRepository, UserRepository userRepository) {
    this.notesRepository = notesRepository;
    this.userRepository = userRepository;
  }

  public List<Note> getAllNotes() {
    String email = "";
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof DefaultOidcUser) {
      email = ((DefaultOidcUser) principal).getAttribute("email");
    } else if (principal instanceof Jwt) {
      email = ((Jwt) principal).getClaim("sub");
    }
    Long uid = userRepository.findByEmail(email).getUid();
    return notesRepository.findByUserUid(uid);
  }

  public Note saveNote(Note note) {
    if (notesRepository.existsByNid(note.getNid())) {
      return notesRepository.saveAndFlush(note);
    }
    return notesRepository.save(note);
  }

  public void deleteNote(Long nid) {
    notesRepository.deleteById(nid);
  }
}
