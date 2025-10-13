package com.sousaarthur.TaskFlow.dto;

import com.sousaarthur.TaskFlow.domain.task.Task;

import jakarta.validation.constraints.NotNull;

public record UpdateTaskDTO(
  @NotNull
  Long id,
  String title,
  String description,
  boolean completed
) {
  public UpdateTaskDTO(Task task){
    this(
      task.getId(),
      task.getTitle(),
      task.getDescription(),
      task.getCompleted()
    );
  }
}
