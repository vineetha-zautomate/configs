package com.hms.configs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Map;

@Converter
public class JsonConverter<T> implements AttributeConverter<T, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert attribute to JSON string: " + attribute, e);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return (T) objectMapper.readValue(dbData, Object.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not convert JSON string to attribute: " + dbData, e);
        }
    }

    // Method to convert JSON string to Map<String, Object>
    public static Map<String, Object> convertFromJson(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            // Handle exception, maybe log it
            return null;
        }
    }

    // If you need to use this method elsewhere (like in the Mapper)
    public static String convertToJson(Map<String, Object> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            // Handle exception
            return null;
        }
    }
}
