package com.rcsoyer.servicosjuridicos.web.rest.errorhandling;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Default api error response
 */
@Getter
@ToString
@EqualsAndHashCode
class ApiError {
    
    private final String message;
    private final String cause;
    
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    
    ApiError(String message, String cause) {
        this.message = message;
        this.cause = cause;
        this.timestamp = LocalDateTime.now();
    }
    
}
