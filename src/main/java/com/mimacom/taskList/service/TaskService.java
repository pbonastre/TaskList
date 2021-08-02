package com.mimacom.taskList.service;

import com.mimacom.taskList.exception.ResourceNotFoundException;
import com.mimacom.taskList.model.Task;
import com.mimacom.taskList.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public Task createTask(Task task) {
       return taskRepository.save(new Task(task.getTitle(), task.getDescription(),task.getFinish()));
    }

    public Task updateTask(Task task) {
        Optional<Task> existingTask = this.taskRepository.findById(task.getId());

        if(existingTask.isPresent()) {
            Task exTask =existingTask.get();
            exTask.setDescription(task.getDescription());
            exTask.setTitle(task.getTitle());
            exTask.setFinish(task.getFinish());
            taskRepository.saveAndFlush(exTask);
            return exTask;
        } else {
            throw new ResourceNotFoundException("Task not found with id : " + task.getId());
        }
    }

    public Task setTaskAsFinish(long taskId, Date finishDate){
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if(existingTask.isPresent()) {
            this.taskRepository.updateFinish(taskId,finishDate);
            this.taskRepository.flush();
            return taskRepository.findById(taskId).get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + taskId);
        }
    }

    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    public Task getTasktById(long taskId) {

        Optional<Task> existingTask = this.taskRepository.findById(taskId);

        if (existingTask.isPresent()) {
            return existingTask.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + taskId);
        }
    }

    public void deleteTask(long taskId) {
        Optional <Task> existingTask = this.taskRepository.findById(taskId);

        if (existingTask.isPresent()) {
            this.taskRepository.delete(existingTask.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + taskId);
        }
    }


}
