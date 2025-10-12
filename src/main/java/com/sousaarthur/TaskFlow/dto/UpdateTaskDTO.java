package com.sousaarthur.TaskFlow.dto;

import com.sousaarthur.TaskFlow.domain.user.User;

import jakarta.validation.constraints.NotNull;

public record UpdateTaskDTO(
  @NotNull
  Long id,
  String title,
  String description,
  User user
) {}
