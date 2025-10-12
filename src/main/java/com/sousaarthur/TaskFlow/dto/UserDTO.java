package com.sousaarthur.TaskFlow.dto;

public record UserDTO(
  String id,
  String login,
  String password,
  String role
) {}
