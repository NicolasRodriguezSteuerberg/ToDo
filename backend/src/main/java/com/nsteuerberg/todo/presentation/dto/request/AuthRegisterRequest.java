package com.nsteuerberg.todo.presentation.dto.request;

public record AuthRegisterRequest(
        String username,
        String email,
        String password,
        String confirmPassword
) {

}
