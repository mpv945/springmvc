package org.haijun.study.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

// https://www.cnblogs.com/shipengzhi/articles/2361333.html
@Component
@Lazy(false)
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    //声明一个静态变量保存
    public static ApplicationContext getContext(){
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }
}
