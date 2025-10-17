package com.sousaarthur.TaskFlow.dto;

import java.time.LocalDateTime;

import com.sousaarthur.TaskFlow.domain.task.Task;

public record TaskDTO(
  Long id,
  String title,
  String description,
  boolean completed,
  LocalDateTime createdAt,
  String userId
) {
  public TaskDTO(Task task){
    this(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted(), task.getCreatedAt(), task.getUser().getId());
  }
}
