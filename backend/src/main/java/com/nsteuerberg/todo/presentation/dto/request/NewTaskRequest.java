package com.nsteuerberg.todo.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NewTaskRequest(
    @NotBlank String title
) {
}
