package com.simonlai.doit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ElementCollection
    private Set<String> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id")
    private List<SubTask> subTasksList;
}

enum TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED
}

enum TaskPriority {
    LOW, MEDIUM, HIGH
}
