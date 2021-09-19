package com.app.util.json;

import com.app.util.log.AppLogger;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonHelper {
    private static final AppLogger log = new AppLogger(JsonHelper.class);
    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(
                    DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    private JsonHelper() {
        // Empty Constructor
    }

    public static String toJson(Object obj) {
        try {
            return OBJ_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("toJson", e);
        }
        return "";
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        final String methodName = "fromJson";
        try {
            return OBJ_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error(methodName, e);
        }

        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            log.error(methodName, "Could not invoke Default Constructor", ex);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        final String methodName = "fromJsonArray";
        try {
            Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + clazz.getName() + ";");
            T[] objects = OBJ_MAPPER.readValue(json, arrayClass);
            return Arrays.asList(objects);
        } catch (ClassNotFoundException | IOException e) {
            log.error(methodName, e);
        }

        return new ArrayList<>();
    }
}
