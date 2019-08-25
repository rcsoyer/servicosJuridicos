package com.rcsoyer.servicosjuridicos.web.rest;

import static java.util.stream.Collectors.toList;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.web.rest.vm.LoggerVM;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/management/logs")
public class LogsResource {
    
    @Timed
    @GetMapping
    public List<LoggerVM> getList() {
        return ((LoggerContext) LoggerFactory.getILoggerFactory())
                   .getLoggerList()
                   .stream()
                   .map(LoggerVM::new)
                   .collect(toList());
    }
    
    @Timed
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLevel(@RequestBody final LoggerVM jsonLogger) {
        final var context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(jsonLogger.getName())
               .setLevel(Level.valueOf(jsonLogger.getLevel()));
    }
    
}
