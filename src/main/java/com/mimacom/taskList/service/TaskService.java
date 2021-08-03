package com.mimacom.taskList.service;

import com.mimacom.taskList.exception.ResourceNotFoundException;
import com.mimacom.taskList.model.Task;
import com.mimacom.taskList.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        log.info("create task");
        return taskRepository.save(new Task(task.getTitle(), task.getDescription(), task.getFinish()));
    }

    public Task updateTask(Task task) {
        Optional<Task> existingTask = this.taskRepository.findById(task.getId());

        if (existingTask.isPresent()) {
            Task exTask = existingTask.get();
            exTask.setDescription(task.getDescription());
            exTask.setTitle(task.getTitle());
            exTask.setFinish(task.getFinish());
            taskRepository.saveAndFlush(exTask);
            return exTask;
        } else {
            log.error(String.format("Task with ID %s not found.",task.getId()));
            throw new ResourceNotFoundException(String.format("Task with ID %s not found.",task.getId()));
        }
    }

    public void setTaskAsFinish(Long taskId, String finishDate) throws ParseException {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if (existingTask.isPresent()) {
            Date formatFinishDate = new SimpleDateFormat("yyyyMMdd").parse(finishDate);
            this.taskRepository.updateFinish(taskId, formatFinishDate);
            this.taskRepository.flush();
        } else {
            log.error(String.format("Task with ID %s not found.",taskId));
            throw new ResourceNotFoundException(String.format("Task with ID %s not found.",taskId));
        }
    }

    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    public Task getTasktById(Long taskId) {
        Optional<Task> existingTask = this.taskRepository.findById(taskId);

        if (existingTask.isPresent()) {
            return existingTask.get();
        } else {
            throw new ResourceNotFoundException(String.format("Task with ID %s not found.",taskId));
        }
    }

    public void deleteTask(Long taskId) {
        Optional<Task> existingTask = this.taskRepository.findById(taskId);

        if (existingTask.isPresent()) {
            this.taskRepository.delete(existingTask.get());
        } else {
            throw new ResourceNotFoundException(String.format("Task with ID %s not found.",taskId));
        }
    }
}
