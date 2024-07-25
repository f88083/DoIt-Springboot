package com.simonlai.doit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private long taskId;

    @Column(nullable = false)
    private String title;
    @Column
    private String description;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private LocalDateTime dueDate;
    @Column(nullable = false)
    private LocalDateTime createDate;
    @Column(nullable = false)
    private LocalDateTime updateDate;
}

//enum TaskStatus {
//    IN_PROGRESS, COMPLETED
//}