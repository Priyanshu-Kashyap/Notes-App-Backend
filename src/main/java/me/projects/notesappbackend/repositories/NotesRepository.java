package me.projects.notesappbackend.repositories;

import me.projects.notesappbackend.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {
  List<Note> findByUserUid(Long uid);

  Boolean existsByNid(Long nid);
}
