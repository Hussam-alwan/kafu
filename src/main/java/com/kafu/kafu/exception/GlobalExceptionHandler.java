package com.kafu.kafu.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.rmi.NoSuchObjectException;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException exc) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setStatus(exc.getApplicationErrorEnum().getStatus().value());
        error.setMessage(exc.getApplicationErrorEnum().getMessage());
        return new ResponseEntity<>(error, exc.getApplicationErrorEnum().getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException exc) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<ApiErrorResponse> handleObjectNotFoundException(NoSuchObjectException exc) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidParameterException(IllegalArgumentException exc) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception exc) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("An unexpected error occurred."+exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}