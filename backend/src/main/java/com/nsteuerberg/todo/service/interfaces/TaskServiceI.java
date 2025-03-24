package com.nsteuerberg.todo.service.interfaces;

import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.TaskResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskServiceI {
    ResponseEntity<TaskResponse> createTask(NewTaskRequest newTaskRequest, String username);
    ResponseEntity<?> deleteTask(Long id, String username);
    ResponseEntity<List<TaskResponse>> getAllTasks(String username);
    ResponseEntity<TaskResponse> getTaskById();
}
