package com.br.tasks.adapters.repository.jpa;

import com.br.tasks.domain.entity.TaskTodo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface TaskTodoJpaRepository extends JpaRepository<TaskTodo, Long> {
    Page<TaskTodo> findByProfileProfileIdAndToCompleteAtBetween(
            Long profileId,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay,
            Pageable pageable
    );
}
