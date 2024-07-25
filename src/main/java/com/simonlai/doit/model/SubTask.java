package com.simonlai.doit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId; // The upper level task ID
    private String title;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
