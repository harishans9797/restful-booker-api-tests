package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JacksonUtils {
    private ObjectMapper mapper;

    public void init() {
        mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(new JodaModule())
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Returns JSON when given an object.
     *
     * @param sourceObject The object being serialized.
     * @return JSON.
     * */
    public String toJson(Object sourceObject) {
        try {
            return mapper.writeValueAsString(sourceObject);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * Gets object from Json.
     *
     * @param json the JSON being converted.
     * @param tClass The class type for the desired object.
     * @return the object from the JSON
    * */

    public <T> T fromJson(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
