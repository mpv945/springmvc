package org.haijun.study.config;

import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

// java 配置类
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })// 导入xml配置文件
public class AppConfig {
    @Autowired
    @Qualifier("cxf")
    private Bus cxfBus;

    @Bean
    public Server jaxRsServer() {
        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        //factory.setServiceBeans(Arrays.<Object>asList(amazonService()));
        factory.setBus(cxfBus);
        factory.setProviders(Arrays.<Object>asList(jsonProvider()));
        return factory.create();
    }

//    @Bean
//    public AmazonService amazonService() {
//        return new AmazonService();
//    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }
}