package com.simonlai.doit.service.impl;

import com.simonlai.doit.dto.TaskRequest;
import com.simonlai.doit.exception.TaskNotFoundException;
import com.simonlai.doit.repository.TaskRepository;
import com.simonlai.doit.model.Task;
import com.simonlai.doit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public long createTask(TaskRequest taskRequest) {
        // Convert from TaskRequest to Task
        Task task = taskRequestConvertToTask(taskRequest);

        // Save to the DB
        task = taskRepository.save(task);
        return task.getTaskId();
    }

    // Update the task
    @Override
    public void updateTask(long taskId, TaskRequest taskRequest) {
        // Find the existing task entity by ID
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        // Update the task
        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStatus(taskRequest.getStatus());
        existingTask.setDueDate(taskRequest.getDueDate());
        existingTask.setUpdateDate(LocalDateTime.now());

        // Save to DB
        taskRepository.save(existingTask);
    }

    // Delete the task by its id
    @Override
    public void deleteTaskById(long taskId) {
        taskRepository.deleteById(taskId);
    }

    // Convert task request to task
    private Task taskRequestConvertToTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setDueDate(taskRequest.getDueDate());

        LocalDateTime now = LocalDateTime.now();
        task.setCreateDate(now);
        task.setUpdateDate(now);

        return task;
    }
}
