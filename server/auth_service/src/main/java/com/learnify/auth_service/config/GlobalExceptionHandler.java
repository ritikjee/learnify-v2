package com.learnify.auth_service.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.learnify.auth_service.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(NoHandlerFoundException ex) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), "Resource not found");
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }
}
