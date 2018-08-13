package org.haijun.study.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// https://www.cnblogs.com/shipengzhi/articles/2361333.html
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

    public final static Object getBean(String beanName){
        return context.getBean(beanName);
    }
    public final static Object getBean(String beanName, Class<?> requiredType) {
        return context.getBean(beanName, requiredType);
    }
    /*public static <T> T getBean(Class<T> clazz) throws BeansException {
        return (T) context.getBean(clazz);
    }*/
}
