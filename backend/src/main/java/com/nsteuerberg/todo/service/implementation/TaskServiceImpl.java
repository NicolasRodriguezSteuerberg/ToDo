package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.persistance.entity.TaskEntity;
import com.nsteuerberg.todo.persistance.repository.TaskRepository;
import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.NewTaskResponse;
import com.nsteuerberg.todo.service.exception.TaskNotFound;
import com.nsteuerberg.todo.service.interfaces.TaskServiceI;
import com.nsteuerberg.todo.util.date.DateTimeFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public ResponseEntity<?> deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() ->
            new TaskNotFound("No se pude borrar debido a que no existe tal tarea")
        );
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
