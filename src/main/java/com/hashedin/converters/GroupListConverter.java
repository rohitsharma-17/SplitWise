package com.hashedin.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.hashedin.entities.Groups;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.List;

@Slf4j
public class GroupListConverter implements AttributeConverter<List<Groups>, String> {
    @Override
    public String convertToDatabaseColumn(List<Groups> groups) {
        if (null == groups) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(groups);
        } catch (JsonProcessingException e) {
            log.error("Error serializing attribute", e);
            return null;
        }
    }

    @Override
    public List<Groups> convertToEntityAttribute(String dbData) {
        if (Strings.isNullOrEmpty(dbData)) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(dbData, new TypeReference<List<Groups>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Error deserializing attribute", e);
            return null;
        }
    }
}
