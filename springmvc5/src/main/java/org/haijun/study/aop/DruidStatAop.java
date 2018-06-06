package org.haijun.study.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Log4j2
public class DruidStatAop {

/*    @After	通知方法会在目标方法返回或抛出异常后调用
    @AfterRetruening	通常方法会在目标方法返回后调用
    @AfterThrowing	通知方法会在目标方法抛出异常后调用
    @Around	通知方法将目标方法封装起来
    @Before	通知方法会在目标方法执行之前执行
    @Pointcut 定义一个公共的切点,其中符合条件的每一个被拦截处为连接点(JoinPoint)。*/
    /**
     * 定义一个公共的切点
     */
/*    @Pointcut("execution(* org.haijun.study.service.*(..))")
    public void performance() {
    }*/


    /**
     * 目标方法执行之前调用
     *//*
    @Before("performance()")
    public void takeSeats(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //Action action = method.getAnnotation(Action.class); // 自己写的类
        //System.out.println("注解式拦截 " + action.name());
    }

    *//**
     * 目标方法执行完后调用
     *//*
    @AfterReturning("performance()")
    public void applause() {
        System.out.println("CLAP CLAP CLAP");
    }

    *//**
     * 目标方法发生异常时调用
     *//*
    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println("Demanding a refund");
    }*/
}
