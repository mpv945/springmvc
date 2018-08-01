package org.haijun.study.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BeanCopierUtils {

    private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<>();

    /**
     * 对基本类型判断扩充
     */
    static {
        primitiveMap.put(String.class, String.class);
        primitiveMap.put(Date.class, Date.class);
    }

    /**
     * the beanCopierMap
     */
    private static final ConcurrentMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    /**
     * @description 两个类对象之间转换
     * @param source
     * @param target
     * @return
     * @return T
     */
    public static <T> T convert(Object source, Class<T> target) {
        T ret = null;
        if (source != null) {
            try {
                ret = target.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("create class[" + target.getName() + "] instance error", e);
            }
            boolean useConverter = false;
            if (source instanceof Collection) {
                useConverter = true;
            }
            BeanCopier beanCopier = getBeanCopier(source.getClass(), target, useConverter);
            if(useConverter){
                beanCopier.copy(source, ret, new Converter() {
                    @Override
                    public Object convert(Object value, Class targetClazz, Object methodName) {
                        if(value instanceof Map){
                            Map values = (Map) value;
                            Map retMap = new HashMap(values.size());
                            for (final Object sourceKey : values.keySet()) {
                                String tempFieldName = methodName.toString().replace("set", "");
                                String fieldName = tempFieldName.substring(0, 1).toLowerCase() + tempFieldName.substring(1);
                                Class clazz = getElementType(targetClazz, fieldName);
                                if(isPrimitive(clazz)){
                                    retMap.put(sourceKey,values.get(sourceKey));
                                }else {
                                    retMap.put(sourceKey,BeanCopierUtils.convert(values.get(sourceKey), clazz));
                                }
                            }
                            return retMap;

                        }else if(value instanceof List){
                            List values = (List) value;
                            List retList = new ArrayList(values.size());
                            for (final Object source : values) {
                                String tempFieldName = methodName.toString().replace("set", "");
                                String fieldName = tempFieldName.substring(0, 1).toLowerCase() + tempFieldName.substring(1);
                                Class clazz = getElementType(targetClazz, fieldName);
                                if(isPrimitive(clazz)){
                                    retList.add(source);
                                }else {
                                    retList.add(BeanCopierUtils.convert(source, clazz));
                                }
                            }
                            return retList;
                        }else if(value instanceof Set){

                        }
                        return value;
                    }
                });
            }else {
                beanCopier.copy(source, ret,null);
            }

        }
        return ret;
    }



    // 内部类说明https://www.jianshu.com/p/f0fdea957792
    /**
     * @description 获取BeanCopier
     * @param source
     * @param target
     * @param useConverter
     * @return
     * @return BeanCopier
     */
    public static BeanCopier getBeanCopier(Class<?> source, Class<?> target, boolean useConverter) {
        String beanCopierKey = generateBeanKey(source, target);
        if (beanCopierMap.containsKey(beanCopierKey)) {
            return beanCopierMap.get(beanCopierKey);
        } else {
            BeanCopier beanCopier = BeanCopier.create(source, target, useConverter);
            // 操作线程安全的Map；此方法解释：如果key对应的值value不存在就put,且返回null。如果key对应的值value已存在，
            // 则返回已存在的值，且value不能为null，否则会报空指针异常。
            beanCopierMap.putIfAbsent(beanCopierKey, beanCopier);
        }
        return beanCopierMap.get(beanCopierKey);
    }

    /**
     * @description 生成两个类的key
     * @param source
     * @param target
     * @return
     * @return String
     */
    public static String generateBeanKey(Class<?> source, Class<?> target) {
        return source.getName() + "@" + target.getName();
    }


    /**
     * @description 获取方法返回值类型
     * @param tartget
     * @param fieldName
     * @return
     * @return Class<?>
     */
    public static Class<?> getElementType(Class<?> tartget, String fieldName) {
        Class<?> elementTypeClass = null;
        try {
            Type type = tartget.getDeclaredField(fieldName).getGenericType();
            ParameterizedType t = (ParameterizedType) type;
            String classStr = t.getActualTypeArguments()[0].toString().replace("class ", "");
            elementTypeClass = Thread.currentThread().getContextClassLoader().loadClass(classStr);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
        }
        return elementTypeClass;
    }

    /**
     * 判断是否是基本类型
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        if (primitiveMap.containsKey(clazz)) {
            return true;
        }
        return clazz.isPrimitive();
    }
}
