package com.mimacom.taskList.service;

import com.mimacom.taskList.exception.ResourceNotFoundException;
import com.mimacom.taskList.model.Task;
import com.mimacom.taskList.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private TaskService taskService;

    @Mock
    TaskRepository taskRepository;

    private List<Task> taskList;
    private Task task1;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        taskService = new TaskService(taskRepository);

        task1 = new Task(1L,"title1", "description1",null );
        var task2 = new Task(2L,"title2", "description2",null);
        var task3 = new Task(3L,"title3", "description3",null);

    }

    @Test
    public void createTask(){
        lenient().doReturn(task1).when(taskRepository).save(any());
        Task taskReturned = taskService.createTask(task1);
        assertEquals("title1", taskReturned.getTitle());
    }

    @Test
    public void updateTask() {
        lenient().doReturn(Optional.of(task1)).when(taskRepository).findById(any());
        Task newT = new Task();
        Task updatedTask = taskService.updateTask(newT);
        assertEquals("title1", updatedTask.getTitle());
    }

    public void setTaskAsFinish(Long taskId, String finishDate) throws ParseException {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if(existingTask.isPresent()) {
            Date formatFinishDate = new SimpleDateFormat("yyyyMMdd").parse(finishDate);
            this.taskRepository.updateFinish(taskId,formatFinishDate);
            this.taskRepository.flush();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + taskId);
        }
    }

    @Test
    public void setTaskAsFinish(){
        lenient().doReturn(Optional.of(task1)).when(taskRepository).findById(any());

    }


}
