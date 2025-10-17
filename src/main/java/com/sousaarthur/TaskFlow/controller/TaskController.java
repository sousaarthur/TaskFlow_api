package com.sousaarthur.TaskFlow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sousaarthur.TaskFlow.domain.task.Task;
import com.sousaarthur.TaskFlow.domain.task.TaskRepository;
import com.sousaarthur.TaskFlow.domain.user.User;
import com.sousaarthur.TaskFlow.domain.user.UserRepository;
import com.sousaarthur.TaskFlow.dto.TaskDTO;
import com.sousaarthur.TaskFlow.dto.TaskStatsDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

  private static final Logger log = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  TaskRepository taskRepository;

  @Autowired
  UserRepository userRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO dto) {
    var principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userRepository.findById(principal.getId()).orElseThrow();
    userRepository.save(user);
    Task task = new Task(dto, user);
    taskRepository.save(task);
    return ResponseEntity.ok(new TaskDTO(task));
  }

  @GetMapping("/list")
  @Transactional
  public ResponseEntity<Page<Task>> listTasks(
      @RequestParam(required = false) Boolean completed,
      @PageableDefault(size = 10, sort = "id") Pageable pageable) {
    var principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Page<Task> tasks = taskRepository.findByUserAndCompleted(principal.getId(), completed, pageable);
    return ResponseEntity.ok(tasks);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<TaskDTO> updateTask(@RequestBody @Valid TaskDTO dto) {
    var task = taskRepository.getReferenceById(dto.id());
    var principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userRepository.getReferenceById(principal.getId());
    if (!task.getUser().getId().equals(principal.getId())) {
      return ResponseEntity.status(403).build();
    }

    task.updateTask(dto);
    taskRepository.save(task);
    return ResponseEntity.ok(new TaskDTO(task));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
    var principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var task = taskRepository.getReferenceById(id);
    if (!task.getUser().getId().equals(principal.getId())) {
      return ResponseEntity.status(403).build();
    }
    return ResponseEntity.ok(new TaskDTO(task));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Task> deleteTaskById(@PathVariable Long id) {
    var principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var task = taskRepository.getReferenceById(id);
    if (!task.getUser().getId().equals(principal.getId())) {
      return ResponseEntity.status(403).build();
    }
    taskRepository.delete(task);
    return ResponseEntity.noContent().build();
  }

    @GetMapping("/stats")
    public ResponseEntity<TaskStatsDTO> getStats() {
        var principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.getReferenceById(principal.getId());

        int total = taskRepository.countByUserId(user.getId());
        int completed = taskRepository.countByUserIdAndCompletedTrue(user.getId());
        int active = taskRepository.countByUserIdAndCompletedFalse(user.getId());

        TaskStatsDTO stats = new TaskStatsDTO(total, completed, active);
        log.info("Stats for user {}: {}", user.getLogin(), stats);

        return ResponseEntity.ok(stats);
    }

}
