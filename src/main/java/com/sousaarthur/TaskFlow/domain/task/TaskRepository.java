package com.sousaarthur.TaskFlow.domain.task;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {
        Page<Task> findByUserId(String userId, Pageable pageable);

        @Query("""
                        SELECT t
                        FROM Task t
                        WHERE t.user.id = :userId
                        AND (:completed IS NULL OR t.completed = :completed)
                        """)
        Page<Task> findByUserAndCompleted(@Param("userId") String userId,
                        @Param("completed") Boolean completed,
                        Pageable pageable);

        int countByUserId(String userId);
        int countByUserIdAndCompletedTrue(String userId);
        int countByUserIdAndCompletedFalse(String userId);
}
