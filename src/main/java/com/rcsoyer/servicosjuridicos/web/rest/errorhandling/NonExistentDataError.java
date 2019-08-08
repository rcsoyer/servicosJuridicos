package com.rcsoyer.servicosjuridicos.web.rest.errorhandling;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * Erro response with http status error 406 for when there was an attempt to modify unknown or nonexistent data in the
 * application
 */
@Getter
@ToString
@EqualsAndHashCode
final class NonExistentDataError {
    
    private final String message;
    private final HttpStatus status;
    
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    
    NonExistentDataError() {
        this.status = HttpStatus.NOT_ACCEPTABLE;
        this.message = "Erro! Tentativa de modificar dados desconhecidos";
        this.timestamp = LocalDateTime.now();
    }
}
