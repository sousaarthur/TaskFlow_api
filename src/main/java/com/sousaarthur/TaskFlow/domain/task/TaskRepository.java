package com.sousaarthur.TaskFlow.domain.task;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
  Page<Task> findByUserId(String userId, Pageable pageable);
}
