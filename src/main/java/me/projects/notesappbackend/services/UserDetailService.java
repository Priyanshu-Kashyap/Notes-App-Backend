package me.projects.notesappbackend.services;

import me.projects.notesappbackend.models.SignupRequest;
import me.projects.notesappbackend.models.User;
import me.projects.notesappbackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class UserDetailService implements UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserDetailService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) throw new UsernameNotFoundException("user not found with username: " + email);
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User save(SignupRequest user) throws Exception {
    if (userRepository.existsByEmail(user.getEmail())) throw new Exception("user already exists");
    User newUser = new User();
    newUser.setAuthProvider("local");
    newUser.setUsername(user.getUsername());
    newUser.setEmail(user.getEmail());
    newUser.setImgUrl(user.getImgUrl());
    newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    newUser.setCreatedAt(new Date());
    return userRepository.save(newUser);
  }

  public User saveOAuth2User(OAuth2User user) {
    if (userRepository.existsByEmail(user.getAttribute("email")))
      return userRepository.findByEmail(user.getAttribute("email"));
    User newUser = new User();
    newUser.setAuthProvider("google");
    newUser.setUsername(user.getAttribute("name"));
    newUser.setEmail(user.getAttribute("email"));
    newUser.setImgUrl(user.getAttribute("picture"));
    newUser.setCreatedAt(new Date());
    return userRepository.save(newUser);
  }
}
