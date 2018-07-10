package org.haijun.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/test")
    public String hello(){
        System.out.println("hell word");
        return "success";// 返回结果=配置视图解析器配置的前缀+"success"+解析器配置的后缀，做转发操作
    }
}
