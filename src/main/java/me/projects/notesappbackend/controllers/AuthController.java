package me.projects.notesappbackend.controllers;

import me.projects.notesappbackend.models.LoginRequest;
import me.projects.notesappbackend.models.LoginResponse;
import me.projects.notesappbackend.models.SignupRequest;
import me.projects.notesappbackend.models.User;
import me.projects.notesappbackend.services.UserDetailService;
import me.projects.notesappbackend.utils.JwtHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
public class AuthController {

  private final JwtHelper jwtHelper;
  private final UserDetailService userDetailService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public AuthController(JwtHelper jwtHelper, UserDetailService userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.jwtHelper = jwtHelper;
    this.userDetailService = userDetailService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @GetMapping("oauth2/user")
  public ResponseEntity<?> oAuth2User(@AuthenticationPrincipal OAuth2User principal) {
    return ResponseEntity.ok(userDetailService.saveOAuth2User(principal));
  }

  @GetMapping("user")
  public ResponseEntity<?> user(@AuthenticationPrincipal Jwt token) {
    return ResponseEntity.ok(userDetailService.getUserByEmail(token.getClaim("sub")));
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    UserDetails user = userDetailService.loadUserByUsername(loginRequest.getEmail());
    User loginUser = userDetailService.getUserByEmail(user.getUsername());
    if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      String jwt = jwtHelper.createJwtForClaims(loginRequest.getEmail(), new HashMap<>());
      return ResponseEntity.ok(new LoginResponse(jwt, loginUser));
    }
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
  }

  @PostMapping("signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) throws Exception {
    return ResponseEntity.ok(userDetailService.save(signupRequest));
  }

  @GetMapping("hello")
  public String hello() {
    return "hello";
  }

}
