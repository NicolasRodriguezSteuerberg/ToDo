package com.nsteuerberg.todo.presentation.dto.response;

public record TaskResponse(
    Long id,
    String title,
    boolean isFinished
) {
}
