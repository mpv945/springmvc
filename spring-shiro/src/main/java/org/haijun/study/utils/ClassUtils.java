package org.haijun.study.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
public class ClassUtils {
    private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<>(9);
    static {
        primitiveMap.put(String.class, String.class);
        primitiveMap.put(Boolean.class, boolean.class);
        primitiveMap.put(Byte.class, byte.class);
        primitiveMap.put(Character.class, char.class);
        primitiveMap.put(Double.class, double.class);
        primitiveMap.put(Float.class, float.class);
        primitiveMap.put(Integer.class, int.class);
        primitiveMap.put(Long.class, long.class);
        primitiveMap.put(Short.class, short.class);
        primitiveMap.put(Date.class, Date.class);
    }
    /**
     * @description 判断基本类型
     * @see     java.lang.String
     * @see     java.lang.Boolean#TYPE
     * @see     java.lang.Byte#TYPE
     * @see     java.lang.Character#TYPE
     * @see     java.lang.Short#TYPE
     * @see     java.lang.Integer#TYPE
     * @see     java.lang.Long#TYPE
     * @see     java.lang.Float#TYPE
     * @see     java.lang.Double#TYPE
     * @see     java.lang.Boolean#TYPE
     * @see     char
     * @see     byte
     * @see     short
     * @see     int
     * @see     long
     * @see     float
     * @see     double
     * @param clazz
     * @return boolean
     */
    public static boolean isPrimitive(Class<?> clazz) {
        if (primitiveMap.containsKey(clazz)) {
            return true;
        }
        return clazz.isPrimitive();
    }
    /**
     * @description 获取方法返回值类型
     * @param tartget
     * @param fieldName
     * @return
     * @return Class<?>
     */

}
