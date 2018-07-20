package org.haijun.study.coreTools.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// 处理异常类,如果在当前请求Handle 找不到@ExceptionHandler标记的处理的异常，会到该类找异常处理类
@ControllerAdvice
public class HandleException {

    // 定义多个ExceptionHandler方法，会先处理最近的异常处理方法
    @ExceptionHandler(value = {ArithmeticException.class})//对这种算术异常进行异常处理
    public ModelAndView handleArithmeticException(Exception ex){// 入参不能传入Map
        System.out.println("出异常了："+ ex);
        ModelAndView mv = new ModelAndView("error");// 视图页面
        mv.addObject("exception",ex);
        return mv; // 跳转到错误页面，如果想把错误带到页面，使用ModelAndView 返回页面
    }

}
