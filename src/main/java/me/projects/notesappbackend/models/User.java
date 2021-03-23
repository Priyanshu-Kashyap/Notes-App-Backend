package me.projects.notesappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long uid;
  private String username;
  @JsonIgnore
  private String password;
  private String email;
  private String imgUrl;
  @CreatedDate
  private Date createdAt;
  private String authProvider;
}
