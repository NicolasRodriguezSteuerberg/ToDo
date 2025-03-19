package com.nsteuerberg.todo.presentation.advice;

import com.nsteuerberg.todo.service.exception.TaskNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTaskNotFound(TaskNotFound taskNotFound) {
        return taskNotFound.getMessage();
    }
}
