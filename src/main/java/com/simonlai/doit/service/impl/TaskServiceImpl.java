package com.simonlai.doit.service.impl;

import com.simonlai.doit.exception.TaskNotFoundException;
import com.simonlai.doit.repository.TaskRepository;
import com.simonlai.doit.model.Task;
import com.simonlai.doit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

}
