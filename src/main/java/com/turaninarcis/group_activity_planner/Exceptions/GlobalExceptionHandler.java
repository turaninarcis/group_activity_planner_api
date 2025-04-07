package com.turaninarcis.group_activity_planner.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFound(ResourceNotFoundException exception){
        return createResponseEntity(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler()
    public ResponseEntity<Map<String,String>> handlePermissionException(PermissionException exception){
        return createResponseEntity(HttpStatus.FORBIDDEN, exception);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleUserExists(UserAlreadyExistsException exception){
        return createResponseEntity(HttpStatus.CONFLICT, exception);
    }
    @ExceptionHandler(UserAlreadyJoinedException.class)
    public ResponseEntity<Map<String,String>> handleUserAlreadyJoined(UserAlreadyJoinedException exception){
        return createResponseEntity(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(ValidationException exception){
        return createResponseEntity(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleGenericException(RuntimeException exception) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePageNotFoundException(PageNotFoundException exception){
        return createResponseEntity(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleAuthentificationFailedException(RuntimeException exception) {

        return createResponseEntity(HttpStatus.BAD_REQUEST, new AuthentificationFailedException());
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<Map<String, String>> handleNotLoggedInException(UserNotLoggedInException exception) {

        return createResponseEntity(HttpStatus.UNAUTHORIZED, exception);
    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Map<String,String>> handleIncorrectPasswordException(IncorrectPasswordException exception){
        return createResponseEntity(HttpStatus.UNAUTHORIZED, exception);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> handleMissingBody(HttpMessageNotReadableException exception){
        return createResponseEntity(HttpStatus.BAD_REQUEST, new RuntimeException("Request body is missing or malformed"));
    }
    private ResponseEntity<Map<String,String>> createResponseEntity(HttpStatus status, RuntimeException e){
        Map<String,String> error = new HashMap<>();
        error.put("error", e.getMessage());

        return ResponseEntity.status(status).body(error);
    }
}
