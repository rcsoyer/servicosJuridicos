package com.rcsoyer.servicosjuridicos.web.rest.errorhandling;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Default api error response
 */
@Getter
@ToString
@EqualsAndHashCode(exclude = "timestamp")
abstract class ApiError {
    
    protected final String message;
    protected final String cause;
    protected final String timestamp;
    
    ApiError(String message, String cause) {
        this.message = message;
        this.cause = cause;
        this.timestamp = LocalDateTime.now()
                                      .format(ofPattern("dd-MM-yyyy hh:mm:ss"));
    }
    
}
