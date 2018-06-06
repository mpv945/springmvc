package org.haijun.study.config;

//@Configuration
public class SecurityConfig {

    // 在异步方法中如何获取用户信息
    // SecurityContextHolder的主要功能是将当前执行的进程和SecurityContext关联起来。
    // SecurityContextHolder.MODE_INHERITABLETHREADLOCAL：用于线程有父子关系的情景中，子线程继承父线程的SecurityContextHolder；
    // SecurityContextHolder.MODE_INHERITABLETHREADLOCAL：全局共用SecurityContextHolder。
/*    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(new String[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
        return methodInvokingFactoryBean;
    }*/

}
