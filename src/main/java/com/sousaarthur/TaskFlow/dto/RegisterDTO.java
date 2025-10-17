package com.sousaarthur.TaskFlow.dto;

import com.sousaarthur.TaskFlow.domain.user.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
  @NotBlank
  @Email
  String login,
  @NotBlank
  String password,
  UserRole role
) {}