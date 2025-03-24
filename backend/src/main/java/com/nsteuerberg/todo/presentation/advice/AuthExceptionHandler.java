package com.nsteuerberg.todo.presentation.advice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nsteuerberg.todo.service.exception.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    private Logger log = LoggerFactory.getLogger(AuthExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String badCredentialException(BadCredentialsException badCredentialsException){
        return badCredentialsException.getMessage();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String usernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        log.error("Error user");
        return usernameNotFoundException.getMessage();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accessDeniedException(AccessDeniedException accessDeniedException){
        log.error(accessDeniedException.getMessage());
        return accessDeniedException.getMessage();
    }
}
