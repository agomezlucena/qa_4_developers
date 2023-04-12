package com.hpcds.gamerestapi.games.infrastructure.rest.errorhandlers;

import com.hpcds.gamerestapi.games.domain.GameAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(GameAlreadyExistException.class)
    public ResponseEntity<Void> manageDuplicatedGameException(GameAlreadyExistException exception){
        return ResponseEntity.badRequest().build();
    }
}
