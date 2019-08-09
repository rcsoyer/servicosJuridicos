package com.rcsoyer.servicosjuridicos.web.rest.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Catch specific exceptions and gives in return proper error responses
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ApiError handleEmptyResultDataAccess(EmptyResultDataAccessException cause) {
        log.warn("There was an attempt to modify nonexistent entity");
        return new ApiError("Erro! Tentativa de modificar dados desconhecidos", cause.getMessage());
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException cause) {
        log.debug("There was an attempt to insert invalid data into the application");
        return new ApiError("Dados inválidos", cause.getMessage());
    }
    
}
