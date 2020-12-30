package com.hashedin.converters;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDateTime;

public class JSONUtil {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper()
                .setSerializationInclusion(Include.NON_NULL);

        // Handling date
        JavaTimeModule timeModule = new JavaTimeModule();

        // For Local date time
        timeModule
                .addSerializer(LocalDateTime.class, new StdSerializer<LocalDateTime>(LocalDateTime.class) {
                    @Override
                    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
                            throws IOException {
                        gen.writeString(DateUtil.toString(value));
                    }
                });
        timeModule.addDeserializer(LocalDateTime.class,
                new StdDeserializer<LocalDateTime>(LocalDateTime.class) {
                    @Override
                    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
                            throws IOException {
                        return DateUtil.parse(p.getText());
                    }
                });

        mapper.registerModule(timeModule);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }


    public static String serialize(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    public static <T> T deserialize(String jsonString, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonString, clazz);
    }

    public static JsonNode createNode(Object object) throws IOException {
        return mapper.readTree(serialize(object));
    }

    public static <T> T deserialize(String jsonString, TypeReference<T> typeReference)
            throws IOException {
        return mapper.readValue(jsonString, typeReference);
    }
}
