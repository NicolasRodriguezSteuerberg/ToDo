package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.persistance.entity.TaskEntity;
import com.nsteuerberg.todo.persistance.repository.TaskRepository;
import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.NewTaskResponse;
import com.nsteuerberg.todo.service.interfaces.TaskServiceI;
import com.nsteuerberg.todo.util.date.DateTimeFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TaskServiceImpl implements TaskServiceI {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ResponseEntity<NewTaskResponse> createTask(NewTaskRequest newTaskRequest) {
        TaskEntity task = TaskEntity.builder()
                .title(newTaskRequest.title())
                .createdIn(DateTimeFormatterUtil.format(LocalDateTime.now()))
                .isFinished(false)
                .build();
        TaskEntity addedEntity = taskRepository.save(task);

        return ResponseEntity.ok(new NewTaskResponse(
                addedEntity.getId(),
                addedEntity.getTitle(),
                addedEntity.isFinished()
        ));
    }
}
