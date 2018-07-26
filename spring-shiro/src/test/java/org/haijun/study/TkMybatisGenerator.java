package org.haijun.study;


import org.junit.Test;
import org.mybatis.generator.api.ShellRunner;

import java.io.InputStream;

/**
 * 生成mybatis文档；官网http://www.mybatis.org/mybatis-3/zh/index.html；http://www.mybatis.org/spring/zh/factorybean.html
 * mysql 8  docker打开方式 https://blog.csdn.net/xinpengfei521/article/details/80403965
 */
public class TkMybatisGenerator {


    @Test
    public void mysqlTk(){

        String[] args =  new String[] { "-configfile", "src\\test\\resources\\tk-generatorConfig.xml", "-overwrite" };
        ShellRunner.main(args);

    }

    public static InputStream getResourceAsStream(String path){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
