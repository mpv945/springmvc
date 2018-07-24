package org.haijun.study.controller;

import org.haijun.study.entity.Test;
import org.haijun.study.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试mybatis
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    ITestService testService;

    @GetMapping("/selectAll")
    public List<Test> test(){
        return testService.getAll();
    }
}
