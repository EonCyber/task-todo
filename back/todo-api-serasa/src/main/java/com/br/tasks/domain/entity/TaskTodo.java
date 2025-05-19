package com.br.tasks.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "task_type", length = 1)
    private String type;

    @Column(name = "task_status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",referencedColumnName = "profile_id")
    private AccountProfile profile;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "to_complete_at")
    private LocalDateTime toCompleteAt;
    @Column(name = "completed_at")
    private LocalDateTime completedAt;




}
