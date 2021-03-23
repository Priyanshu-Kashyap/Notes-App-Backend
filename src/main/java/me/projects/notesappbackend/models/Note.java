package me.projects.notesappbackend.models;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "notes")
public class Note {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long nid;
  private String title;
  private String content;
  private Boolean archive;
  private Boolean trash;
  @ElementCollection
  private List<String> images;
  @LastModifiedDate
  private Date editedAt;
  @ManyToOne
  @JoinColumn(name = "uid", nullable = false)
  private User user;

}
