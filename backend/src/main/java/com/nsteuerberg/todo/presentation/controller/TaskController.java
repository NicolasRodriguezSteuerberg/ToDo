package com.nsteuerberg.todo.presentation.controller;

import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.service.implementation.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping("")
    public ResponseEntity<?> addTask(@RequestBody @Valid NewTaskRequest newTask) {
        return taskService.createTask(newTask);
    }
}
