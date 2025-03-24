package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.persistance.entity.TaskEntity;
import com.nsteuerberg.todo.persistance.entity.UserEntity;
import com.nsteuerberg.todo.persistance.repository.TaskRepository;
import com.nsteuerberg.todo.persistance.repository.UserRepository;
import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.response.TaskResponse;
import com.nsteuerberg.todo.service.exception.AccessDeniedException;
import com.nsteuerberg.todo.service.exception.TaskNotFound;
import com.nsteuerberg.todo.service.interfaces.TaskServiceI;
import com.nsteuerberg.todo.util.date.DateTimeFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskServiceI {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<TaskResponse> createTask(NewTaskRequest newTaskRequest, String username) {
        UserEntity user = userRepository.findByUserName(username).orElseThrow(() -> new BadCredentialsException("aaa"));
        TaskEntity task = TaskEntity.builder()
                .title(newTaskRequest.title())
                .createdIn(DateTimeFormatterUtil.format(LocalDateTime.now()))
                .isFinished(false)
                .user(user)
                .build();
        TaskEntity addedEntity = taskRepository.save(task);

        return ResponseEntity.ok(new TaskResponse(
                addedEntity.getId(),
                addedEntity.getTitle(),
                addedEntity.isFinished()
        ));
    }

    @Override
    public ResponseEntity<?> deleteTask(Long id, String username) {
        TaskEntity task = taskRepository.findById(id).orElseThrow(() ->
            new TaskNotFound("No se pude borrar debido a que no existe tal tarea")
        );
        if (!task.getUser().getUserName().equals(username)){
            throw new AccessDeniedException("El usuario " + username + " no es propietario de la tarea");
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getAllTasks(String username) {
        UserEntity userEntity = userRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado")
        );
        List<TaskResponse> taskList = new ArrayList<>();
        // it does another query to take the tasks
        userEntity.getTasks().forEach(task ->
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
