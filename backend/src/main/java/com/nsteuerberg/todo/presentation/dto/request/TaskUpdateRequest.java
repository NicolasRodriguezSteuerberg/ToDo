package com.nsteuerberg.todo.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record TaskUpdateRequest(
        @NotNull Long id,
        String title,
        Boolean isFinished
) {
}
