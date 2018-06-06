package org.haijun.study.utils.mapper;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

public class BeanMapper {

    private static Mapper dozer = DozerBeanMapperBuilder.buildDefault();

    /**
     * 映射对象
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * list对象映射
     * @param sourceList
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>(sourceList.size());
        Iterator var3 = sourceList.iterator();

        while(var3.hasNext()) {
            Object sourceObject = var3.next();
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }

        return destinationList;
    }

    /**
     * 两个对象复制，不返回对象
     * @param source
     * @param destinationObject
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    /**
     * 复制隐射Map对象
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> toMap(Object target) {
        return toMap(target,false);
    }

    /**
     * 将目标对象的所有属性转换成Map对象
     *
     * @param target 目标对象
     * @param ignoreParent 是否忽略父类的属性
     *
     * @return Map
     */
    public static <T> Map<String, T> toMap(Object target,boolean ignoreParent) {
        return toMap(target,ignoreParent,false);
    }

    /**
     * 将目标对象的所有属性转换成Map对象
     *
     * @param target 目标对象
     * @param ignoreParent 是否忽略父类的属性
     * @param ignoreEmptyValue 是否不把空值添加到Map中
     *
     * @return Map
     */
    public static <T> Map<String, T> toMap(Object target,boolean ignoreParent,boolean ignoreEmptyValue) {
        return toMap(target,ignoreParent,ignoreEmptyValue,new String[0]);
    }

    /**
     * 将目标对象的所有属性转换成Map对象
     *
     * @param target 目标对象
     * @param ignoreParent 是否忽略父类的属性
     * @param ignoreEmptyValue 是否不把空值添加到Map中
     * @param ignoreProperties 不需要添加到Map的属性名
     */
    public static <T> Map<String, T> toMap(Object target, boolean ignoreParent, boolean ignoreEmptyValue, String... ignoreProperties) {
        Map<String, T> map = new HashMap<String, T>();

        Set<Field> fields = null;
        if(ignoreParent){
            fields = ReflectionUtils.getFields(target.getClass());// 查询全部的字段，不包含父类？
        }else {
            fields = ReflectionUtils.getAllFields(target.getClass());// 查询全部的字段，包含父类
        }

        for (Iterator<Field> it = fields.iterator(); it.hasNext();) {
            Field field = it.next();
            T value = null;

            try {
                value = (T) field.get(target);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (ignoreEmptyValue
                    && ((value == null || value.toString().equals(""))
                    || (value instanceof Collection && ((Collection<?>) value).isEmpty())
                    || (value instanceof Map && ((Map<?,?>)value).isEmpty()))) {
                continue;
            }

            boolean flag = true;
            String key = field.getName();

            for (String ignoreProperty:ignoreProperties) {
                if (key.equals(ignoreProperty)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                map.put(key, value);
            }
        }

        return map;
    }
}
