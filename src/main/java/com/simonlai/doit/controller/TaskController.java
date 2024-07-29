package com.simonlai.doit.controller;

import com.simonlai.doit.dto.TaskRequest;
import com.simonlai.doit.model.Task;
import com.simonlai.doit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/getall")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);

        // Found the task
        if (task != null) {
            return ResponseEntity.status(HttpStatus.OK).body(task);
        }

        // Not found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest taskRequest) {
         // create and get the taskId
         long taskId = taskService.createTask(taskRequest);

         // return the created task
         return ResponseEntity.status(HttpStatus.CREATED).body(taskService.getTaskById(taskId));

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable long taskId, @RequestBody TaskRequest taskRequest) {
        // Check if the task exists
        Task task = taskService.getTaskById(taskId);

        // Didn't find the task
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Exists, update it
        taskService.updateTask(taskId, taskRequest);

        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable long taskId) {
        taskService.deleteTaskById(taskId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
