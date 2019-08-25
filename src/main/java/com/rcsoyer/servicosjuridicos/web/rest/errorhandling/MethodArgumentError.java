package com.rcsoyer.servicosjuridicos.web.rest.errorhandling;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
final class MethodArgumentError extends ApiError {
    
    private final List<ObjectError> errors;
    
    MethodArgumentError(final MethodArgumentNotValidException argsNotValidExc) {
        super("Dados inválidos", argsNotValidExc.getMessage());
        this.errors = argsNotValidExc.getBindingResult().getAllErrors();
    }
    
}
