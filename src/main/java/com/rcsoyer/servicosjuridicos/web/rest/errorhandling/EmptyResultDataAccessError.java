package com.rcsoyer.servicosjuridicos.web.rest.errorhandling;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.dao.EmptyResultDataAccessException;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class EmptyResultDataAccessError extends ApiError {
    
    EmptyResultDataAccessError(final EmptyResultDataAccessException emptyResultDataExc) {
        super("Erro! Tentativa de modificar dados desconhecidos",
              emptyResultDataExc
                  .getMostSpecificCause()
                  .getMessage());
    }
    
}
