package org.haijun.study.cxf.impl;

import org.haijun.study.cxf.Baeldung;
import org.haijun.study.vo.Student;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service("baeldung")
@WebService(endpointInterface = "org.haijun.study.cxf.Baeldung",serviceName = "testCxf1")
public class BaeldungImpl implements Baeldung {

    private int counter;

    @Override
    public String hello(String name) {
        return "Hello " + name + "!";
    }

    @Override
    public String register(Student student) {
        counter++;
        return student.getName() + " is registered student number " + counter;
    }
}
