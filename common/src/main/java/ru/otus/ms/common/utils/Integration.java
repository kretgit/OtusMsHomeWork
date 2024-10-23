package ru.otus.ms.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public final class Integration {

    public static final String JSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Integration() {
        throw new IllegalStateException("Utility class");
    }

    /*
     * Jackson serialization/deserialization
     */
    public static final ObjectMapper mapper = new ObjectMapper()
            .setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT))
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static final ObjectMapper mapperSnakeCase = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT))
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static <T> T fromJson(Class<T> clazz, String json) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("JSON parse error: " + e);
        }
        return null;
    }

    public static Object fromJson(String className, String json) throws ClassNotFoundException {
        try {
            return mapper.readValue(json, ClassLoader.getSystemClassLoader().loadClass(className));
        } catch (JsonProcessingException e) {
            log.error("JSON parse error: " + e);
        }
        return null;
    }

    public static String toJsonCompact(Object target) {
        String result = "";
        try {
            result = mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            log.error("Write object error " + e);
        }
        return result;
    }

    public static String toJson(Object target) {
        String result = "";
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(target);
        } catch (JsonProcessingException e) {
            log.error("Write object error " + e);
        }
        return result;
    }

    public static String toJsonSnakeCase(Object target) {
        String result = "";
        try {
            result = mapperSnakeCase.writerWithDefaultPrettyPrinter().writeValueAsString(target);
        } catch (JsonProcessingException e) {
            log.error("Write object error " + e);
        }
        return result;
    }

    public static String toJsonCompactSnakeCase(Object target) {
        String result = "";
        try {
            result = mapperSnakeCase.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            log.error("Write object error " + e);
        }
        return result;
    }

    /*
     * Gson serialization/deserialization
     */
    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat(JSON_DATE_FORMAT)
            .create();

    public static final Gson gsonCompact = new GsonBuilder().create();

    public static ToGson toGson(Object target) {
        return new ToGson(target, false);
    }

    public static ToGson toGsonCompact(Object target) {
        return new ToGson(target, true);
    }


    public static class ToGson {
        private final Object target;
        private final boolean compact;

        private ToGson(Object target, boolean compact) {
            this.target = target;
            this.compact = compact;
        }

        @Override
        public String toString() {
            if (compact) {
                return gsonCompact.toJson(target);
            } else {
                return gson.toJson(target);
            }
        }
    }

    public static <T> T fromGson(Class<T> clazz, String json) {
        return gson.fromJson(json, clazz);
    }

    public static Object fromGson(String className, String json) throws ClassNotFoundException {
        return gson.fromJson(json, ClassLoader.getSystemClassLoader().loadClass(className));
    }

    public static <T> T fromGson(Class<T> clazz, String json, FieldNamingPolicy namingPolicy, String dateFormat) {
        Gson gsonExt = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat(dateFormat)
                .setFieldNamingStrategy(namingPolicy)
                .create();
        return gsonExt.fromJson(json, clazz);
    }

    public static <T> List<T> listFromGson(Class<T> clazz, String json, FieldNamingPolicy namingPolicy, String dateFormat) {
        Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        Gson gsonExt = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat(dateFormat)
                .setFieldNamingStrategy(namingPolicy)
                .create();
        return gsonExt.fromJson(json, listType);
    }

    public static <T> List<T> listFromGson(Class<T> clazz, String json) {
        Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        return gson.fromJson(json, listType);
    }

    public static List<?> listFromGson(String className, String json) throws ClassNotFoundException {
        Class clazz = ClassLoader.getSystemClassLoader().loadClass(className);
        Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        return gson.fromJson(json, listType);
    }

    public static <K,V> Map<K,V> mapFromGson(String json, Class<K> keyClass, Class<V> valueClass) {
        Type mapType = TypeToken.getParameterized(HashMap.class, keyClass, valueClass).getType();
        return gson.fromJson(json, mapType);
    }

}
