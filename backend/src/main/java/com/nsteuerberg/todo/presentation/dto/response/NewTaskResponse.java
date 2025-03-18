package com.nsteuerberg.todo.presentation.dto.response;

public record NewTaskResponse(
    Long id,
    String title,
    boolean isFinished
) {
}
