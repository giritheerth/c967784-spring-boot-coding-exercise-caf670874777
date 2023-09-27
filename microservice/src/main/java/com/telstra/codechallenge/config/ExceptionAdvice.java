package com.telstra.codechallenge.config;


import com.telstra.codechallenge.exception.ErrorResponse;
import com.telstra.codechallenge.exception.GitDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /**
     * Handle Exception if GitDataNotFound.
     */
    @ExceptionHandler(value = GitDataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(GitDataNotFoundException e) {
        ErrorResponse response = new ErrorResponse("DATA_NOTFOUND_ERROR", e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle Exception if illegalArgumentException.
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {

        ErrorResponse response = new ErrorResponse("INVALID_ARGUMENTS_ERROR", e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus((HttpStatus.BAD_REQUEST.value()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handle Exception for generic.
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        log.error("Exception {}", e.getLocalizedMessage());
        ErrorResponse response = new ErrorResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.name());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

