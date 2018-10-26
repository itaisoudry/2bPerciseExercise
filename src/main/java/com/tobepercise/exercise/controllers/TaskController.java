package com.tobepercise.exercise.controllers;

import com.tobepercise.exercise.exceptions.TaskControllerException;
import com.tobepercise.exercise.model.Task;
import com.tobepercise.exercise.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TaskController {
    @Autowired
    private TasksService taskService;


    @GetMapping("/task/{employeeId}")
    public ResponseEntity getEmployeeTasks(@PathVariable int employeeId) throws TaskControllerException {
        if (employeeId <= 0) {
            throw new TaskControllerException("employeeId must be bigger than 0");
        }

        return ResponseEntity.ok().body(this.taskService.getEmployeeTasks(employeeId));
    }


    @PostMapping("/task")
    public ResponseEntity createTask(@Valid @RequestBody Task task) throws TaskControllerException {
        if (task == null) {
            throw new TaskControllerException("Task must not be null");
        }

        this.taskService.create(task);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/task")
    public ResponseEntity updateTask(@Valid @RequestBody Task task) throws TaskControllerException {
        if (task == null) {
            throw new TaskControllerException("Task must not be null");
        }

        this.taskService.create(task);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity deleteTask(@PathVariable int taskId) throws TaskControllerException {
        if (taskId <= 0) {
            throw new TaskControllerException("taskId must be bigger than 0");
        }

        this.taskService.delete(taskId);

        return ResponseEntity.ok().build();
    }
}
