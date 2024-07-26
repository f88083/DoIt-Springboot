package com.simonlai.doit.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
