package me.projects.notesappbackend.models;

import lombok.Data;

@Data
public class LoginResponse {
  private final String token;
  private final User user;

  public LoginResponse(String token, User user) {
    this.token = token;
    this.user = user;
  }
}
