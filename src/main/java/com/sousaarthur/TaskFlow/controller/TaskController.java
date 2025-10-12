package com.sousaarthur.TaskFlow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sousaarthur.TaskFlow.domain.task.Task;
import com.sousaarthur.TaskFlow.domain.task.TaskRepository;
import com.sousaarthur.TaskFlow.domain.user.User;
import com.sousaarthur.TaskFlow.dto.TaskDTO;
import com.sousaarthur.TaskFlow.dto.UpdateTaskDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
  
  @Autowired
  TaskRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDTO dto){
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Task task = new Task(dto, user);
    repository.save(task);
    return ResponseEntity.ok(task);
  }

  @GetMapping("/list")
  @Transactional
  public ResponseEntity<Page<Task>> listTasks(@PageableDefault(size = 10, sort = "id") Pageable pageable){
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Page<Task> tasks = repository.findByUserId(user.getId(), pageable);
    return ResponseEntity.ok(tasks);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<Task> updateTask(@RequestBody @Valid UpdateTaskDTO dto){
    var task = repository.getReferenceById(dto.id());
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    task = new Task(dto, user);
    repository.save(task);
    return ResponseEntity.ok(task);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Task> deleteTaskById(@PathVariable Long id){
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var task = repository.getReferenceById(id);
    if(!task.getUser().getId().equals(user.getId())){
      return ResponseEntity.status(403).build();
    }
    repository.delete(task);
    return ResponseEntity.noContent().build();
  }

}
