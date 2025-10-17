package com.sousaarthur.TaskFlow.dto;

public record TaskStatsDTO(
    int totalTasks,
    int completedTasks,
    int activeTasks) {
}
