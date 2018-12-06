package org.haijun.study.annotation;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyGetter {

    /**
     * 根据key获取国际化资源的值
     * @param msg
     * @param country
     * @return
     */
    public static String getProperty(String msg, String country){
        Locale localeUS=new Locale("en","US");
        // message根据自己配置的资源化文件基本信息来确定
        ResourceBundle bundle=ResourceBundle.getBundle("message",localeUS);
        return bundle.getString(msg);
    }
}
