package com.nsteuerberg.todo.presentation.dto.request;

public record AuthLogInRequest(
        String username,
        String password
) {
}
