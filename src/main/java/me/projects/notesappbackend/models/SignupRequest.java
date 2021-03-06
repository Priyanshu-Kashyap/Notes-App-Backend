package me.projects.notesappbackend.models;

import lombok.Data;

@Data
public class SignupRequest {
  private String username;
  private String email;
  private String password;
  private String imgUrl;
  private String authProvider;
}
