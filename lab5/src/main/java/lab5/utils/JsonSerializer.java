package lab5.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class JsonSerializer {
    public static class LazyHolder {
        public static final JsonSerializer INSTANCE = new JsonSerializer();
    }

    private static final Logger log = LogManager.getLogger(JsonSerializer.class);

    private final ObjectMapper objectMapper;

    private JsonSerializer() {
        objectMapper = new ObjectMapper();
        configureObjectMapper();
    }

    private void configureObjectMapper() {
//        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    public JsonMessage serialize(Serializable serializable) {
        try {
            return new JsonMessage(objectMapper.writeValueAsString(serializable));
        } catch (JsonProcessingException e) {
            log.fatal("on serializing: ", e);
            return null;
        }
    }

    public Serializable deserialize(JsonMessage jsonMessage, Class<?> clazz) {
        try {
            return (Serializable) objectMapper.readValue(jsonMessage.getMessage(), clazz);
        }
        catch (JsonProcessingException e) {
            log.error("on deserializing: ", e);
            return null;
        }
        catch (ClassCastException e) {
            log.error("ClassCastException because of : " + clazz.getName());
            log.error("on deserializing: ", e);
            return null;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class JsonMessage implements Serializable {
        private final String message;
    }
}
