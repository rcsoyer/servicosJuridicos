package com.rcsoyer.servicosjuridicos.service.dto;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Class to manually convert from JSON to any Object
 *
 * @author rcsoyer
 */
final class JsonConverter {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    private JsonConverter() {
    }
    
    /**
     * convert from String JSON to any Object
     *
     * @param 'String'
     * @param 'Class<T>'
     * @return T
     * @throws
     * @throws JsonMappingException
     * @throws IOException
     */
    static <T> T readValue(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }
}
