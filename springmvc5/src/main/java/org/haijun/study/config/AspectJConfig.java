package org.haijun.study.config;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import lombok.extern.log4j.Log4j2;
import org.haijun.study.aop.DruidStatAop;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
// @ComponentScan("org.light4j.sping4.base.aop") // 可以通过@Bean申明或者配置扫描
@EnableAspectJAutoProxy(proxyTargetClass=true) //不管bean是否实现了接口都采用CGLIB代理
@Log4j2
public class AspectJConfig {

    /**
     * https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_Druid%E5%92%8CSpring%E5%85%B3%E8%81%94%E7%9B%91%E6%8E%A7%E9%85%8D%E7%BD%AE
     * 监听Spring
     *  1.定义拦截器
     *  2.定义切入点
     *  3.定义通知类
     * @return
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor(){
        return new DruidStatInterceptor();
    }
    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut(){
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        String patterns = "org.haijun.study.repository.*";
        String patterns1 = "org.haijun.study.service.*";
        //String patterns2 = "com.github.darains.*.repository.*";
        druidStatPointcut.setPatterns(patterns,patterns1);
        return druidStatPointcut;
    }
    @Bean
    public Advisor druidStatAdvisor() {
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }
    // druid根据类型拦截（上面根据aop拦截）
/*    @Bean
    public ApplicationContextAware beanTypeAutoProxyCreator(){
        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
        beanTypeAutoProxyCreator.setTargetBeanType(BaseService.class);
        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        beanTypeAutoProxyCreator.setProxyTargetClass(true);
        try {
            beanTypeAutoProxyCreator.afterPropertiesSet();
        } catch (Exception e) {
            //TODO
        }
        return beanTypeAutoProxyCreator;
    }*/

/*    //使用BeanNameAutoProxyCreator来创建代理
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
        BeanNameAutoProxyCreator beanNameAutoProxyCreator=new BeanNameAutoProxyCreator();
        //设置要创建代理的那些Bean的名字
        beanNameAutoProxyCreator.setBeanNames("userSer*");
        //设置拦截链名字(这些拦截器是有先后顺序的)
        beanNameAutoProxyCreator.setInterceptorNames("myMethodInterceptor");
        return beanNameAutoProxyCreator;
    }*/

    @Bean
    public DruidStatAop druidStatAop(){
        return new DruidStatAop();
    }
}
