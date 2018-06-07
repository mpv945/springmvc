package org.haijun.study.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfig {

/*    // cxf 配置 (没有成功)
    // 它为Apache CXF提供了与Spring Framework一起工作的扩展
    @Bean(name = Bus.DEFAULT_BUS_ID, destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }
    @Bean
    public JaxWsServiceFactoryBean jaxWsServiceFactoryBean() {
        JaxWsServiceFactoryBean item = new JaxWsServiceFactoryBean();
        item.setWrapped(true);
        return item;
    }
    // 一个EnpointImpl bean也需要使用SpringBus bean和一个Web服务实现者来创建。这个bean用来在给定的HTTP地址发布端点：
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(cxf(), new BaeldungImpl());

        //endpoint.setServiceFactory(jaxWsServiceFactoryBean());
        endpoint.publish("/baeldung");////接口发布在 /NetbarServices 目录下;http://localhost:8080/services/baeldung
        return endpoint;
    }*/
}
