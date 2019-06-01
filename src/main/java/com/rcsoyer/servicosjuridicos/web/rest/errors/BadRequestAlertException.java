package com.rcsoyer.servicosjuridicos.web.rest.errors;

import java.net.URI;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestAlertException extends AbstractThrowableProblem {
    
    private static final long serialVersionUID = -8518368110360363962L;
    
    private final String errorKey;
    private final String entityName;
    
    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }
    
    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
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
}
