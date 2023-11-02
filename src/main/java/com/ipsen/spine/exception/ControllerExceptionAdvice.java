package com.ipsen.spine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({
            BadCredentialsException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResult notFoundError(BadCredentialsException err) {
        err.printStackTrace();
        return new ErrorResult("Invalid email or password.");
    }

    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult notFoundError(NotFoundException err) {
        err.printStackTrace();
        return new ErrorResult(err.getMessage());
    }

    @ExceptionHandler({
            Exception.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult serverError(RuntimeException err) {
        err.printStackTrace();
        return new ErrorResult(err.getMessage());
    }

}
