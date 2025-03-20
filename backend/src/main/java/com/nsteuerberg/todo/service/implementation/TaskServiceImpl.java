package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.persistance.entity.TaskEntity;
import com.nsteuerberg.todo.persistance.repository.TaskRepository;
import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.TaskResponse;
import com.nsteuerberg.todo.service.exception.TaskNotFound;
import com.nsteuerberg.todo.service.interfaces.TaskServiceI;
import com.nsteuerberg.todo.util.date.DateTimeFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskServiceI {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ResponseEntity<TaskResponse> createTask(NewTaskRequest newTaskRequest) {
        TaskEntity task = TaskEntity.builder()
                .title(newTaskRequest.title())
                .createdIn(DateTimeFormatterUtil.format(LocalDateTime.now()))
                .isFinished(false)
                .build();
        TaskEntity addedEntity = taskRepository.save(task);

        return ResponseEntity.ok(new TaskResponse(
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

    @Override
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        // ToDo cambiarlo para que busque por el id del usuario
        List<TaskResponse> taskList = new ArrayList<>();
        taskRepository.findAll().forEach(task ->
            taskList.add(
                    new TaskResponse(
                            task.getId(),
                            task.getTitle(),
                            task.isFinished()
                    )
            )
        );
        return ResponseEntity.ok(taskList);
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById() {
        return null;
    }
}
