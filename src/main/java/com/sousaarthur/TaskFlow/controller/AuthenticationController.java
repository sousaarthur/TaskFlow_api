package com.sousaarthur.TaskFlow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sousaarthur.TaskFlow.domain.user.User;
import com.sousaarthur.TaskFlow.domain.user.UserRepository;
import com.sousaarthur.TaskFlow.dto.AuthenticationDTO;
import com.sousaarthur.TaskFlow.dto.LoginResponseDTO;
import com.sousaarthur.TaskFlow.dto.RegisterDTO;
import com.sousaarthur.TaskFlow.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired 
  private UserRepository repository;

  @Autowired
  private TokenService tokenService;
  
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto){
    var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((User) auth.getPrincipal());
    return ResponseEntity.ok(new LoginResponseDTO(token));
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO dto){
    if(this.repository.findByLogin(dto.login()) != null){
      return ResponseEntity.badRequest().body(null);
    }
    String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
    User user = new User(dto.login(), encryptedPassword);
    this.repository.save(user);
    return ResponseEntity.ok(user);
  }
}
