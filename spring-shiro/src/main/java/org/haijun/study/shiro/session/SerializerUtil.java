package org.haijun.study.shiro.session;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class SerializerUtil {

    private static final JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

    /**
     * 序列化对象
     * @param obj
     * @return
     */
    public static <T> byte[] serialize(T obj){
        try {
            return jdkSerializationRedisSerializer.serialize(obj);
        } catch (Exception e) {
            throw new RuntimeException("序列化失败!", e);
        }
    }

    /**
     * 反序列化对象
     * @param bytes 字节数组
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes){
        try {
            return (T) jdkSerializationRedisSerializer.deserialize(bytes);
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败!", e);
        }
    }

}
