package com.simonlai.doit.service.impl;

import com.simonlai.doit.dto.TaskRequest;
import com.simonlai.doit.exception.TaskNotFoundException;
import com.simonlai.doit.model.Task;
import com.simonlai.doit.repository.TaskRepository;
import com.simonlai.doit.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TaskServiceImpl.class)
class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void getAllTasks() {
        // [Arrange] expected data(correct data)
        List<Task> expectedTasksList = new ArrayList<>();
        Task task1 = new Task(
                1,
                "task1",
                "task 1 for testing",
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Task task2 = new Task(
                2,
                "task2",
                "task 2 for testing",
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Task task3 = new Task(
                3,
                "task3",
                "task 3 for testing",
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Add to the expected tasks list
        expectedTasksList.add(task1);
        expectedTasksList.add(task2);
        expectedTasksList.add(task3);

        // Mock the call to "taskRepository.findAll()"
        Mockito.when(taskRepository.findAll()).thenReturn(expectedTasksList);

        // [Act] call from the service
        List<Task> actualTasks = taskService.getAllTasks();

        // Check if the same
        assertEquals(expectedTasksList, actualTasks);
    }

    @Test
    void getTaskById() {
        Task expectedTask = new Task(
                1,
                "task1",
                "task 1 for testing",
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mocking calling findById
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(expectedTask));

        Task actualTask = taskService.getTaskById(1L);

        assertEquals(expectedTask, actualTask);
    }

    @Test
    void getTaskById_withValidId_shouldReturnTask() {
        // Arrange
        Long taskId = 1L;
        Task expectedTask = new Task(taskId, "Task 1", "Description 1", 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        // Act
        Task actualTask = taskService.getTaskById(taskId);

        // Assert
        assertEquals(expectedTask, actualTask);

        // Check the method has only been called once
        Mockito.verify(taskRepository, Mockito.times(1)).findById(taskId);
    }

    @Test
    void getTaskById_withInvalidId_shouldThrowTaskNotFoundException() {
        // Arrange
        Long taskId = 1L;
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(taskId);
    }

    @Test
    void createTask_shouldSaveAndReturnTaskId() {
        // Arrange
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("New Task");
        taskRequest.setDescription("New Description");
        taskRequest.setStatus(0);
        taskRequest.setDueDate(LocalDateTime.now().plusDays(1));

        Task savedTask = new Task(1L, taskRequest.getTitle(), taskRequest.getDescription(), taskRequest.getStatus(),
                taskRequest.getDueDate(), LocalDateTime.now(), LocalDateTime.now());
//         Mockito.when(taskService.taskRequestConvertToTask(taskRequest)).thenReturn(task);
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(savedTask);

        // Act
        long taskId = taskService.createTask(taskRequest);

        // Assert
        assertEquals(1L, taskId);

        // Verify the correct Task object was saved
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        Mockito.verify(taskRepository).save(taskCaptor.capture());
        Task capturedTask = taskCaptor.getValue();

        assertNotNull(capturedTask, "The task passed to repository.save() should not be null");
        assertEquals(taskRequest.getTitle(), capturedTask.getTitle());
        assertEquals(taskRequest.getDescription(), capturedTask.getDescription());
        assertEquals(taskRequest.getStatus(), capturedTask.getStatus());
        assertEquals(taskRequest.getDueDate(), capturedTask.getDueDate());
        assertNotNull(capturedTask.getCreateDate());
        assertNotNull(capturedTask.getUpdateDate());
    }

    @Test
    void updateTask_shouldUpdateExistingTask() {
        // Arrange
        long taskId = 1L;
        Task taskGoingToBeUpdated = new Task();
        taskGoingToBeUpdated.setTaskId(taskId);
        taskGoingToBeUpdated.setTitle("Old Title");
        taskGoingToBeUpdated.setDescription("Old Description");
        taskGoingToBeUpdated.setStatus(0);
        taskGoingToBeUpdated.setDueDate(LocalDateTime.now());
        taskGoingToBeUpdated.setCreateDate(LocalDateTime.now().minusDays(1));
        taskGoingToBeUpdated.setUpdateDate(LocalDateTime.now().minusDays(1));

        // Preserve the old task
        Task oldTask = new Task(
                taskGoingToBeUpdated.getTaskId(),
                taskGoingToBeUpdated.getTitle(),
                taskGoingToBeUpdated.getDescription(),
                taskGoingToBeUpdated.getStatus(),
                taskGoingToBeUpdated.getDueDate(),
                taskGoingToBeUpdated.getCreateDate(),
                taskGoingToBeUpdated.getUpdateDate()
        );

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("New Title");
        taskRequest.setDescription("New Description");
        taskRequest.setStatus(1);
        taskRequest.setDueDate(LocalDateTime.now().plusDays(1));

        // Mock when call findById
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskGoingToBeUpdated));

        // Act
        taskService.updateTask(taskId, taskRequest);

        assertEquals(taskRequest.getTitle(), taskGoingToBeUpdated.getTitle());
        assertEquals(taskRequest.getDescription(), taskGoingToBeUpdated.getDescription());
        assertEquals(taskRequest.getStatus(), taskGoingToBeUpdated.getStatus());
        assertEquals(taskRequest.getDueDate(), taskGoingToBeUpdated.getDueDate());
        assertNotEquals(oldTask.getUpdateDate(), taskGoingToBeUpdated.getUpdateDate());
        assertTrue(taskGoingToBeUpdated.getUpdateDate().isAfter(oldTask.getUpdateDate()));
    }

    @Test
    void updateTask_shouldThrowExceptionForNonExistentTask() {
        // Arrange
        long nonExistentTaskId = 999L;
        Mockito.when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());

        TaskRequest taskRequest = new TaskRequest();

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(nonExistentTaskId, taskRequest);
        });

        // Verify if taskRepository has never called "save" method
        Mockito.verify(taskRepository, Mockito.never()).save(Mockito.any(Task.class));
    }

    @Test
    void deleteTaskById_shouldCallRepositoryMethod() {
        // Arrange
        long taskId = 1L;

        // Act
        taskService.deleteTaskById(taskId);

        // Assert
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
    }

    @Test
    void deleteTaskById_shouldNotThrowExceptionForNonExistentTask() {
        // Arrange
        long nonExistentTaskId = 999L;

        // We don't need to set up any specific behavior for the mock,
        // as JpaRepository's deleteById doesn't throw an exception for non-existent IDs

        // Act & Assert
        assertDoesNotThrow(() -> {
            taskService.deleteTaskById(nonExistentTaskId);
        });

        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(nonExistentTaskId);
    }
}