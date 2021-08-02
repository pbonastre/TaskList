package com.mimacom.taskList.controller;

import com.mimacom.taskList.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mimacom.taskList.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TaskListControllerTest {

    @Mock
    private TaskService taskService;

    private TaskListController taskListController;

    private List<Task> taskList;
    private Task task1;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.taskListController = new TaskListController(taskService);
        mockMvc = MockMvcBuilders.standaloneSetup(taskListController).build();

        task1 = new Task("title1", "description1");
        var task2 = new Task("title2", "description2");
        var task3 = new Task("title3", "description3");
        taskList = Arrays.asList(task1, task2, task3);
    }

    @Test
    public void createTask() {
        lenient().doReturn(task1).when(taskService).createTask(any());
        ResponseEntity<Task> response = taskListController.createTask(task1);
        assertNotNull(response);
        assertEquals("Title1", response.getBody().getTitle());
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
        ResponseEntity<Task> response = taskListController.updateTaskById(Long.getLong("1"), task1);
        assertNotNull(response);
        assertEquals("title1", response.getBody().getTitle());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
   }

    @Test
    public void setTaskAsFinish() throws ParseException {
        lenient().doNothing().when(taskService).setTaskAsFinish(any(), any());
        ResponseEntity<String> response = taskListController.setTaskAsFinish(Long.valueOf(1), "20210725");
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    public void getTaskById(){
        lenient().doReturn(task1).when(taskService).getTasktById(any());
        ResponseEntity<Task> response = taskListController.getTaskById(Long.valueOf(1));
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteTask() throws Exception {
        lenient().doNothing().when(taskService).deleteTask(any());
        HttpStatus httpStatus = taskListController.deleteTask(Long.valueOf(1));
        assertNotNull(httpStatus);
        assertEquals(HttpStatus.OK, httpStatus);
    }
}