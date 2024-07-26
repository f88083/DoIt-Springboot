package com.simonlai.doit.service;

import com.simonlai.doit.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    List<Task> getAllTasks();

    Task getTaskById(Long taskId);
}
