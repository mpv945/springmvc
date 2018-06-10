package org.haijun.study.cxf.impl;

import org.haijun.study.cxf.Baeldung;
import org.haijun.study.vo.Student;
import org.haijun.study.vo.User;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<User> getusers() {
        List<User> list = new ArrayList<>();
        User user1= new User();
        user1.setUsername("haijun");
        user1.setPassword("pwd");
        list.add(user1);
        return list;
    }
}
