package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.persistance.entity.TaskEntity;
import com.nsteuerberg.todo.persistance.entity.UserEntity;
import com.nsteuerberg.todo.persistance.repository.TaskRepository;
import com.nsteuerberg.todo.persistance.repository.UserRepository;
import com.nsteuerberg.todo.presentation.dto.request.NewTaskRequest;
import com.nsteuerberg.todo.presentation.dto.request.TaskUpdateRequest;
import com.nsteuerberg.todo.presentation.dto.response.TaskResponse;
import com.nsteuerberg.todo.service.exception.AccessDeniedException;
import com.nsteuerberg.todo.service.exception.TaskNotFound;
import com.nsteuerberg.todo.service.exception.TaskValidationException;
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
        String date = DateTimeFormatterUtil.format(LocalDateTime.now());
        TaskEntity task = TaskEntity.builder()
                .title(newTaskRequest.title())
                .createdIn(date)
                .isFinished(false)
                .lastUpdateIn(date)
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
        // sort list by isFinished and createdIn/finishedIn

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

    @Override
    public ResponseEntity<TaskResponse> updateTask(TaskUpdateRequest updateRequest, String username) {
        TaskEntity task = taskRepository.findById(updateRequest.id()).orElseThrow(() ->
                new TaskNotFound("No existe la tarea con el id: " + updateRequest.id())
        );
        if (!task.getUser().getUserName().equals(username)){
            throw new AccessDeniedException("No eres el propietario de la tarea");
        }
        boolean updatedFlag = false;
        if (updateRequest.isFinished() != null && updateRequest.isFinished() != task.isFinished()){
            task.setFinished(updateRequest.isFinished());
            updatedFlag = true;
        }
        if (updateRequest.title() != null && !updateRequest.title().equals(task.getTitle())){
            task.setTitle(updateRequest.title());
            updatedFlag = true;
        }
        if (!updatedFlag) throw new TaskValidationException("Los datos recibidos son los mismos que los existentes");
        task.setLastUpdateIn(DateTimeFormatterUtil.format(LocalDateTime.now()));
        TaskEntity taskUpdated = taskRepository.save(task);
        return ResponseEntity.ok().body(
                new TaskResponse(
                        taskUpdated.getId(),
                        task.getTitle(),
                        task.isFinished()
                )
        );
    }
}
