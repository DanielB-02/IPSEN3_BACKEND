package com.ipsen.spine.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({
            BadCredentialsException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResult notFoundError(BadCredentialsException err) {
        log.warn("Failed sign-in attempt: {}", err.getMessage());
        return new ErrorResult("Invalid email or password.");
    }

    @ExceptionHandler({
            AccessDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResult accessDenied(AccessDeniedException err) {
        return new ErrorResult("You do not have the permission for that operation");
    }

    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult notFoundError(NotFoundException err) {
        log.info("Not found: {}", err.getMessage());
        return new ErrorResult(err.getMessage());
    }

    @ExceptionHandler({
            Exception.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult serverError(RuntimeException err) {
        log.error("Unhandled exception", err);
        return new ErrorResult(err.getMessage());
    }

}
