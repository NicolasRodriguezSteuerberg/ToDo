package com.nsteuerberg.todo.service.interfaces;

import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.NewTaskResponse;
import org.springframework.http.ResponseEntity;

public interface TaskServiceI {
    ResponseEntity<NewTaskResponse> createTask(NewTaskRequest newTaskRequest);
}
