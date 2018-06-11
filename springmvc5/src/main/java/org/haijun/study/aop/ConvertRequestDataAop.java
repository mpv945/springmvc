package org.haijun.study.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Log4j2
public class ConvertRequestDataAop {

    //切入点 @Pointcut("execution(public * *(..)) && @annotation(demo.APM)")
    @Pointcut("@annotation(org.haijun.study.annotation.ConvertRequestData)")
    public void myPoint() {
    }

    // @Around表示包围一个函数，也就是可以在函数执行前做一些事情，也可以在函数执行后做一些事情
    @Around("myPoint()")//@Around("execution(* me.laiyijie.demo.service.UserServiceImpl.sayHello(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {// ProceedingJoinPoint，在使用了@Around之后可以带入这个参数，代表的其实就是sayHello这个函数，不过做了一些封装
        log.info("数据转换拦截器进入处理开始");
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        //拦截的实体类
        Object target = joinPoint.getTarget();
        //拦截的方法名称
        String methodName = joinPoint.getSignature().getName();
        //拦截的方法参数
        Object[] args = joinPoint.getArgs();
        //拦截的放参数类型
        Class[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();

        //TODO 处理业务代码



        //处理完之后放行
        //Object[] args = joinPoint.getArgs();
        return joinPoint.proceed(args); // 就是相当于执行了 代理的方法！
    }
}
