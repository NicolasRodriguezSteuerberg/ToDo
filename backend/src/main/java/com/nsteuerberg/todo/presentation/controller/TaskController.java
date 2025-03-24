package com.nsteuerberg.todo.presentation.controller;

import com.nsteuerberg.todo.persistance.entity.TaskEntity;
import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.TaskResponse;
import com.nsteuerberg.todo.service.implementation.TaskServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskServiceImpl taskService;

    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getAllTasks(Principal principal){
        String username = principal.getName();
        return taskService.getAllTasks(username);
    }

    // necesario?
    @GetMapping("{id}")
    public ResponseEntity<?> getTask() {
        return null;
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> addTask(@RequestBody @Valid NewTaskRequest newTask, Principal principal) {
        String username = principal.getName();
        log.info(username);
        return taskService.createTask(newTask, username);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Principal principal){
        // ToDo recoger el usuario y eliminar solo si es suya
        return taskService.deleteTask(id, principal.getName());
    }
}
