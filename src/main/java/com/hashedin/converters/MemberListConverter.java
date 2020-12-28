package com.hashedin.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.hashedin.entities.Users;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.TreeSet;

@Slf4j
public class MemberListConverter implements AttributeConverter<TreeSet<Users>, String> {
    @Override
    public String convertToDatabaseColumn(TreeSet<Users> strings) {
        if (null == strings) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            log.error("Error serializing attribute", e);
            return null;
        }
    }

    @Override
    public TreeSet<Users> convertToEntityAttribute(String dbData) {
        if (Strings.isNullOrEmpty(dbData)) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(dbData, new TypeReference<TreeSet<Users>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Error deserializing attribute", e);
            return null;
        }
    }
}
