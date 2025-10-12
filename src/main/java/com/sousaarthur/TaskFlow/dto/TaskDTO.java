package com.sousaarthur.TaskFlow.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(
  Long id,
  @NotBlank
  String title,
  String description,
  boolean completed,
  LocalDateTime createdAt
) {}
