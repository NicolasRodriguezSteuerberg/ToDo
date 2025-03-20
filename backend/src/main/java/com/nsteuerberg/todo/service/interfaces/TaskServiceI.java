package com.nsteuerberg.todo.service.interfaces;

import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.TaskResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskServiceI {
    ResponseEntity<TaskResponse> createTask(NewTaskRequest newTaskRequest);
    ResponseEntity<?> deleteTask(Long id);
    ResponseEntity<List<TaskResponse>> getAllTasks();
    ResponseEntity<TaskResponse> getTaskById();
}
