package com.example.JuegoDeDadosMongodbPersistence.Excepcions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> handlePlayerNotFoundException(PlayerNotFoundException ex) {
        String response = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PlayerNameAlreadyUsedException.class)
    public ResponseEntity<Object> handlePlayerNameAlreadyUsedException(PlayerNameAlreadyUsedException ex) {
        String response = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
