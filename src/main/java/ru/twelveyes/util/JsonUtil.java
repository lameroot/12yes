package ru.twelveyes.util;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.twelveyes.domain.ContactParam;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * User: Krainov
 * Date: 28.02.14
 * Time: 16:56
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String toJson(Object o) throws IOException {
        StringWriter writer = new StringWriter();
        objectMapper.writeValue(writer,o);
        return writer.toString();
    }

    public static String toSafeJson(Object o) {
        try{
            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer,o);
            return writer.toString();
        } catch (Exception e) {
            logger.warn("Error to Json",e);
        }
        return null;
    }

    public static <T extends Object> T toObject(Class<T> clazz, String json) throws IOException {
        return objectMapper.readValue(json,clazz);
    }

}
