package com.nsteuerberg.todo.presentation.dto.response;

public record AuthTokensResponse(
        String refreshToken,
        String accessToken
) {
}
