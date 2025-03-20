package com.nsteuerberg.todo.presentation.controller;

import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.TaskResponse;
import com.nsteuerberg.todo.service.implementation.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return taskService.getAllTasks();
    }

    // necesario?
    @GetMapping("{id}")
    public ResponseEntity<?> getTask() {
        return null;
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> addTask(@RequestBody @Valid NewTaskRequest newTask) {
        // ToDo recoger el usuario y añadirle el id
        return taskService.createTask(newTask);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        // ToDo recoger el usuario y eliminar solo si es suya
        return taskService.deleteTask(id);
    }
}
