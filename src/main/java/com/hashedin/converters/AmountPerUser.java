package com.hashedin.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.Map;

@Slf4j
public class AmountPerUser implements AttributeConverter<Map<String, Double>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Double> stringDoubleMap) {
        if (null == stringDoubleMap) {
            return null;
        }
        try {
            return JSONUtil.getMapper().writeValueAsString(stringDoubleMap);
        } catch (JsonProcessingException e) {
            log.error("Error serializing attribute", e);
            return null;
        }
    }

    @Override
    public Map<String, Double> convertToEntityAttribute(String dbData) {
        if (Strings.isNullOrEmpty(dbData)) {
            return null;
        }
        try {
            return JSONUtil.getMapper().readValue(dbData, new TypeReference<Map<String, Double>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Error deserializing attribute", e);
            return null;
        }
    }
}
