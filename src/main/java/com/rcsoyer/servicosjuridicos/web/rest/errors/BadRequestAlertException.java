package com.rcsoyer.servicosjuridicos.web.rest.errors;

import java.net.URI;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestAlertException extends AbstractThrowableProblem {
    
    private static final long serialVersionUID = -8518368110360363962L;
    
    private final String errorKey;
    private final String entityName;
    
    BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null,
              getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }
    
    public String getEntityName() {
        return entityName;
    }
    
    public String getErrorKey() {
        return errorKey;
    }
    
    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        return Map.of("message", "error." + errorKey,
                      "params", entityName);
    }
    
    public static BadRequestAlertExceptionBuilder builder() {
        return new BadRequestAlertExceptionBuilder();
    }
    
    public static final class BadRequestAlertExceptionBuilder {
        
        private URI type;
        private String errorKey;
        private String entityName;
        private String defaultMessage;
        
        private BadRequestAlertExceptionBuilder() {
        }
        
        public BadRequestAlertExceptionBuilder type(final URI type) {
            this.type = type;
            return this;
        }
        
        public BadRequestAlertExceptionBuilder defaultMessage(String defaultMessage) {
            this.defaultMessage = defaultMessage;
            return this;
        }
        
        public BadRequestAlertExceptionBuilder entityName(String entityName) {
            this.entityName = entityName;
            return this;
        }
        
        public BadRequestAlertExceptionBuilder errorKey(String errorKey) {
            this.errorKey = errorKey;
            return this;
        }
        
        public BadRequestAlertException build() {
            return new BadRequestAlertException(type, defaultMessage, entityName, errorKey);
        }
        
    }
}
