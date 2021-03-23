package me.projects.notesappbackend.models;

import lombok.Data;

@Data
public class LoginRequest {
  private String email;
  private String password;
}
