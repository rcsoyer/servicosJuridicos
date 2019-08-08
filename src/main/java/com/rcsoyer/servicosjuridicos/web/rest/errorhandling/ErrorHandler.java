package com.rcsoyer.servicosjuridicos.web.rest.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public NonExistentDataError handleEmptyResultDataAccess() {
        log.error("There was an attempt to modify nonexistent entity");
        return new NonExistentDataError();
    }
    
}
