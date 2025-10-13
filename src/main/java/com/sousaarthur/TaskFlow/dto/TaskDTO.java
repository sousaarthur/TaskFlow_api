package com.sousaarthur.TaskFlow.dto;

import java.time.LocalDateTime;

import com.sousaarthur.TaskFlow.domain.task.Task;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(
  Long id,
  @NotBlank
  String title,
  String description,
  boolean completed,
  LocalDateTime createdAt
) {
  public TaskDTO(Task task){
    this(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted(), task.getCreatedAt());
  }
}
