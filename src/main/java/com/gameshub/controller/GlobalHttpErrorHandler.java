package com.gameshub.controller;

import com.gameshub.exception.*;
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

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<Object> handleUserNotVerifiedException(UserNotVerifiedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
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

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Object> handleGameNotFoundException(GameNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameOpinionNotFoundException.class)
    public ResponseEntity<Object> handleGameOpinionNotFoundException(GameOpinionNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameOpinionWithProfanitiesException.class)
    public ResponseEntity<Object> handleGameOpinionWithProfanitiesException(GameOpinionWithProfanitiesException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GameOpinionsListNotFoundException.class)
    public ResponseEntity<Object> handleGameOpinionsListNotFoundException(GameOpinionsListNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameOpinionUpdateTimeExpiredException.class)
    public ResponseEntity<Object> handleGameOpinionUpdateTimeExpiredException(GameOpinionUpdateTimeExpiredException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GameOpinionUpdateAccessDeniedException.class)
    public ResponseEntity<Object> handleGameOpinionUpdateAccessDeniedException(GameOpinionUpdateAccessDeniedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(GameAlreadySubscribedException.class)
    public ResponseEntity<Object> handleGameAlreadySubscribedException(GameAlreadySubscribedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GameNotSubscribedException.class)
    public ResponseEntity<Object> handleGameNotSubscribedException(GameNotSubscribedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
