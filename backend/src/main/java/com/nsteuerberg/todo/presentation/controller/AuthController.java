package com.nsteuerberg.todo.presentation.controller;

import com.nsteuerberg.todo.presentation.dto.request.AuthLogInRequest;
import com.nsteuerberg.todo.presentation.dto.response.AuthTokensResponse;
import com.nsteuerberg.todo.service.implementation.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @PostMapping
    public ResponseEntity<AuthTokensResponse> logIn(@RequestBody AuthLogInRequest authLogInRequest){
        return ResponseEntity.ok(userDetailService.login(authLogInRequest));
    }
}
