package com.rcsoyer.servicosjuridicos.web.rest.vm;

import ch.qos.logback.classic.Logger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * View Model object for storing a Logback logger.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class LoggerVM {
    
    private String name;
    private String level;
    
    public LoggerVM(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }
    
}
