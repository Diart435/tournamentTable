package com.example.tournamentTable.Handler;

import com.example.tournamentTable.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({PlayerNotFoundException.class, TeamNotFoundException.class, GamesNotFoundException.class, PlayerNotInTeamException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> ErrorNotFound(RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationsException(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler({PlayerAlreadyExistsException.class, TeamAlreadyExistsException.class, TeamHaveGamesException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> ErrorAlreadyExists(RuntimeException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
