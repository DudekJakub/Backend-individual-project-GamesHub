package com.gameshub.controller;

import com.gameshub.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyVerifiedException.class)
    public ResponseEntity<Object> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserEmailAlreadyExistsInDatabaseException.class)
    public ResponseEntity<Object> handleUserEmailAlreadyExistsInDatabaseException(UserEmailAlreadyExistsInDatabaseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserLoginNameAlreadyExistsInDatabaseException.class)
    public ResponseEntity<Object> handleUserLoginNameAlreadyExistsInDatabaseException(UserLoginNameAlreadyExistsInDatabaseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Object> handlePasswordNotMatchException(PasswordNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(RawgGameDetailedNotFoundException.class)
    public ResponseEntity<Object> handleRawgGameDetailedNotFoundException(RawgGameDetailedNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
