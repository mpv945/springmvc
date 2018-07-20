package org.haijun.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class ErrorTestController {

    // 定义多个ExceptionHandler方法，会先处理最近的异常处理方法
    @ExceptionHandler(value = {ArithmeticException.class})//对这种算术异常进行异常处理
    public String handleArithmeticException(Exception ex){// 入参不能传入Map
        System.out.println("出异常了："+ ex);
        return "error"; // 跳转到错误页面，如果想把错误带到页面，使用ModelAndView 返回页面
    }

    @RequestMapping(value = "/jisuan" ,method = RequestMethod.GET)
    public String jisuan(@RequestParam("chushu") Long val){
        System.out.println("处罚计算="+(10/val));
        return "success";
    }
}
