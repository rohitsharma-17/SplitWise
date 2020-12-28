package com.hashedin.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.hashedin.entities.Bills;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.List;

@Slf4j
public class BillListConverter implements AttributeConverter<List<Bills>,String> {
    @Override
    public String convertToDatabaseColumn(List<Bills> bills) {
        if (null == bills) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(bills);
        } catch (JsonProcessingException e) {
            log.error("Error serializing attribute", e);
            return null;
        }
    }

    @Override
    public List<Bills> convertToEntityAttribute(String dbData) {
        if (Strings.isNullOrEmpty(dbData)) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(dbData, new TypeReference<List<Bills>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Error deserializing attribute", e);
            return null;
        }
    }
}
