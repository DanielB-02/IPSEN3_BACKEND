package com.ipsen.spine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult notFoundError(NotFoundException err) {
        return new ErrorResult(err.getMessage());
    }

    @ExceptionHandler({
            Exception.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult serverError(RuntimeException err) {
        return new ErrorResult(err.getMessage());
    }

}
