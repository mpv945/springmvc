package org.haijun.study.controller;

import org.haijun.study.entity.Test;
import org.haijun.study.entity.TkTest;
import org.haijun.study.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试mybatis
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Value("${sds}")
    private String test;

/*    @Value("${dd}")
    private String dd;*/

    @Autowired
    ITestService testService;

    @GetMapping("/selectRange")
    public List<Test> test(){
        System.out.println("数据库读取的配置="+test);
        return testService.getAll();
    }

    @GetMapping("/selectTkAll")
    public List<TkTest> testTk(){
        return testService.getTkAll();
    }

    /*测试url： localhost:8083/test/tkAdd?name=小兵&email=7766@qq.com&age=45*/
    @GetMapping("/tkAdd")
    public boolean addTk(@RequestParam(value = "name" ,required = true) String name,
                         @RequestParam("email") String email,
                         @RequestParam("age") Integer age){
        TkTest obj = new TkTest();
        obj.setName(name);
        obj.setEmail(email);
        obj.setAge(age);
        return testService.add(obj);
    }
}
