package com.mimacom.taskList.controller;


import com.mimacom.taskList.exception.ResourceNotFoundException;
import com.mimacom.taskList.model.Task;
import com.mimacom.taskList.service.TaskService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
@Api(value= "taskListController", description = "There are six different microservice to process a task.We could create, update and delete tasks as well as set a task to a finish state.")
public class TaskListController {

    private final TaskService taskService;

    @ApiOperation(value = "", notes = "Creates a new Task object for the current user. ", response = Task.class, tags = {})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Task created successfully", response = Task.class)})
    @PostMapping("")
    public ResponseEntity<Task> createTask(
            @ApiParam(value = "Task to add to the list", required = true)
            @RequestBody Task task){
        log.info("Creating task");
        return ResponseEntity.ok().body(this.taskService.createTask(task));
    }

    @ApiOperation(value = "", notes = "Gets the list of Task objects for the current user. ", response = Task.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "An array of Task objects", response = Task.class)})
    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @ApiOperation(value = "", notes = "Updates an existing Task object. ", response = Task.class, tags = {})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Task updated successfully", response = Task.class)})
    @PutMapping("/update")
    public ResponseEntity<?> updateTaskById(@ApiParam(value = "Task identifier", example="1", required = true) @RequestParam("id") Long id,
                                               @ApiParam(value = "Task to update", required = true) @RequestBody Task task) {
        try {
            log.info("Updating task with ID "+ id);
            task.setId(id);
            return ResponseEntity.ok().body(this.taskService.updateTask(task));
        } catch (ResourceNotFoundException e) {
            log.error("Invalid input parameters found",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Task:%s has not been found",id));
        }
    }

    @ApiOperation(value = "", notes = "Set as finish an existing task.  ", response = Task.class, tags = {})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Task set as Finish", response = Task.class)})
    @PutMapping("/finish")
    public ResponseEntity<String> setTaskAsFinish(@ApiParam(value = "Task identifier",example="1", required = true) @RequestParam("id") Long id,
                                                @ApiParam(value = "Date set for finish",example = "20210225", format="yyyyMMdd",required = true) @RequestParam("finishDate") String finishDate) {
        try {
            log.info("Set task ID "+id+" as finished.");
            this.taskService.setTaskAsFinish(id,finishDate);
            return ResponseEntity.ok().body(String.format("Task:%s has been set as finished",id));
        } catch (ParseException e) {
            log.error("Task with ID"+id+" not found", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Could not set task %s as finished. Task does not exists.",id));
        }
    }

    @ApiOperation(value = "", notes = "Get a Task by ID for the current user. ", response = Task.class, tags = {})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "A Task object", response = Task.class)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@ApiParam(value = "Task identifier",example="1", required = true) @PathVariable("id") Long id) {
        try {
            log.info("Get task with  ID "+id);
            return ResponseEntity.ok().body(taskService.getTasktById(id));
        } catch (ResourceNotFoundException e) {
            log.error("Task with ID"+id+" not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Task:%s has not been found",id));
        }
    }

    @ApiOperation(value = "", notes = "Remove a Task. ", response = Void.class, tags = {})
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Task deleted successfully", response = Void.class)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@ApiParam(value = "Task identifier", example="1",required = true) @PathVariable("id") Long id) {
        try {
            this.taskService.deleteTask(id);
            log.info("Task  ID "+id+" has been remove");
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Task:%s has been removed",id));
        } catch (ResourceNotFoundException e) {
            log.error("Task with ID"+id+" not found and could not be remove", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Task:%s has not been removed",id));
        }
    }
}
