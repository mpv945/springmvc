package org.haijun.study.utils.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonMapper {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();



    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json结果集转化为对象 （map和list 是否支持）
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            //e.printStackTrace();
            //LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将对象转换成json字符串。
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> String toJson(T entity) {
        try {
            return MAPPER.writeValueAsString(entity);
        } catch (Exception e) {
            //e.printStackTrace();
            //LOG.error(e.getMessage(), e);
        }
        return null;
    }

}
