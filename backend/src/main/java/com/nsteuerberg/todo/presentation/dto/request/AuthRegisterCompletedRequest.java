package com.nsteuerberg.todo.presentation.dto.request;

import com.nsteuerberg.todo.util.constant.RoleEnum;

public record AuthRegisterCompletedRequest (
        String username,
        String email,
        String password,
        String confirmPassword,
        RoleEnum role
) {
}
