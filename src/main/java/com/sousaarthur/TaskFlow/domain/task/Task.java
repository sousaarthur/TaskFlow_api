package com.sousaarthur.TaskFlow.domain.task;

import java.time.LocalDateTime;

import com.sousaarthur.TaskFlow.domain.user.User;
import com.sousaarthur.TaskFlow.dto.TaskDTO;
import com.sousaarthur.TaskFlow.dto.UpdateTaskDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String title;
  String description;
  boolean completed;
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public boolean getCompleted(){
    return this.completed;
  }

  public void updateTask(UpdateTaskDTO dto){
    if(dto.title() != null){
      this.title = dto.title();
    }
    if(dto.description() != null){
      this.description = dto.description();
    }
    this.completed = dto.completed();
  }
  
  public Task(TaskDTO dto, User user) {
    this.id = dto.id();
    this.title = dto.title();
    this.description = dto.description();
    this.completed = dto.completed();
    this.createdAt = dto.createdAt();
    this.user = user;
  }


}