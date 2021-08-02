package com.mimacom.taskList.service;

import com.mimacom.taskList.exception.ResourceNotFoundException;
import com.mimacom.taskList.model.Task;
import com.mimacom.taskList.repositories.TaskRepository;
import com.mimacom.taskList.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;
    private TaskService taskService;
    private List<Task> taskList;
    private Task task1;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        taskService = new TaskService(taskRepository);
        task1 = new Task(1L, "title1", "description1", TestUtils.parseDate("20210225"));
        var task2 = new Task(2L, "title2", "description2", null);
        var task3 = new Task(3L, "title3", "description3", null);
        taskList = Arrays.asList(task1, task2, task3);
        lenient().doReturn(Optional.of(task1)).when(taskRepository).findById(any());

    }

    @Test
    public void createTask() {
        lenient().doReturn(task1).when(taskRepository).save(any());
        Task taskReturned = taskService.createTask(task1);
        Assertions.assertEquals("title1", taskReturned.getTitle());
    }

    @Test
    public void updateTask() {
        Task newT = new Task("title2","description2");
        Task updatedTask = taskService.updateTask(newT);
        Assertions.assertEquals("title2", updatedTask.getTitle());
    }

    @Test
    public void setTaskAsFinish() {
       Assertions.assertDoesNotThrow(() -> taskService.setTaskAsFinish(1L, "20210225"));
    }

    @Test
    public void setTaskAsFinishException() {
        lenient().doReturn(Optional.empty()).when(taskRepository).findById(any());
        ResourceNotFoundException resourceE = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskService.setTaskAsFinish(33L, "20210725");
        });
        Assertions.assertEquals("Record not found with id : 33", resourceE.getMessage());
    }

    @Test
    public void getAllTasks() {
        lenient().doReturn(taskList).when(taskRepository).findAll();
        List<Task> taskListReturned = taskService.getAllTasks();
        Assertions.assertNotNull(taskListReturned);
        Assertions.assertEquals(3, taskListReturned.size());
    }

    public Task getTasktById(Long taskId) {

        Optional<Task> existingTask = this.taskRepository.findById(taskId);

        if (existingTask.isPresent()) {
            return existingTask.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + taskId);
        }
    }

    @Test
    public void getTasktById() {
        Task existingtask = taskService.getTasktById(1L);
        Assertions.assertEquals("title1", existingtask.getTitle());
    }

    @Test
    public void getTasktByIdException() {
        lenient().doReturn(Optional.empty()).when(taskRepository).findById(any());
        ResourceNotFoundException resourceE = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTasktById(33L);
        });
        Assertions.assertEquals("Record not found with id : 33", resourceE.getMessage());
    }

    @Test
    public void deleteTask() {
        Assertions.assertDoesNotThrow(() -> taskService.deleteTask(1L));
    }

    @Test
    public void deleteTaskException() {
        lenient().doReturn(Optional.empty()).when(taskRepository).findById(any());
        ResourceNotFoundException resourceE = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskService.deleteTask(33L);
        });
        Assertions.assertEquals("Record not found with id : 33", resourceE.getMessage());
    }
}
