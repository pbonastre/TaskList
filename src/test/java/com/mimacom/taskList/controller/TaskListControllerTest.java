package com.mimacom.taskList.controller;

import com.mimacom.taskList.exception.ResourceNotFoundException;
import com.mimacom.taskList.model.Task;
import com.mimacom.taskList.repositories.TaskRepository;
import com.mimacom.taskList.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TaskListControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private TaskListController taskListController;

    private List<Task> taskList;
    private Task task1;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        taskListController = new TaskListController(taskService);
        mockMvc = MockMvcBuilders.standaloneSetup(taskListController).build();

        task1 = new Task(1L,"title1", "description1",null);
        var task2 = new Task(2L,"title2", "description2",null);
        var task3 = new Task(3L,"title3", "description3",null);
        taskList = Arrays.asList(task1, task2, task3);
    }

    @Test
    public void createTask() {
        lenient().doReturn(task1).when(taskService).createTask(any());
        ResponseEntity<Task> response = taskListController.createTask(task1);
        assertNotNull(response);
        assertEquals("title1", response.getBody().getTitle());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getAllTasks() {
        lenient().doReturn(taskList).when(taskService).getAllTasks();
        ResponseEntity<List<Task>> response = taskListController.getAllTasks();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody().size(), 3);
    }

    @Test
    public void updateTaskById() {
        lenient().doReturn(task1).when(taskService).updateTask(any());
        ResponseEntity<Task> response = (ResponseEntity<Task>) taskListController.updateTaskById(1L, task1);
        assertNotNull(response);
        assertEquals("title1", response.getBody().getTitle());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
   }

    @Test
    public void setTaskAsFinish() throws ParseException {
        Assertions.assertDoesNotThrow(()->taskListController.setTaskAsFinish(1L, "20210725"));
    }

    @Test
    public void setTaskAsFinishException() throws ParseException {
        lenient().doThrow(new ResourceNotFoundException("Task not found")).when(taskService).setTaskAsFinish(33L, "20210725");
        ResourceNotFoundException resourceE = Assertions.assertThrows(ResourceNotFoundException.class, () ->{
            taskListController.setTaskAsFinish(Long.valueOf(33), "20210725");
        });

        Assertions.assertEquals("Task not found",resourceE.getMessage());
    }


    @Test
    public void getTaskById(){
        lenient().doReturn(task1).when(taskService).getTasktById(1L);
        ResponseEntity<Task> response = (ResponseEntity<Task>) taskListController.getTaskById(1L);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteTask() throws Exception {
        lenient().doReturn(Optional.of(task1)).when(taskRepository).findById(any());
         ResponseEntity<String> httpStatus = taskListController.deleteTask(1L);
        assertNotNull(httpStatus);
        assertEquals(HttpStatus.OK, httpStatus.getStatusCode());
    }
}