package com.oscgc.securevideo.server.tool;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("rawtypes")
public class JsonUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseJson(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map parseMap(String content) {
        try {
            return objectMapper.readValue(content, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List parseList(String content) {
        try {
            return objectMapper.readValue(content, List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object obj) {
        StringWriter sw = new StringWriter();
        try {
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(sw);
            objectMapper.writeValue(jsonGenerator, obj);
            jsonGenerator.flush();
            return sw.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
