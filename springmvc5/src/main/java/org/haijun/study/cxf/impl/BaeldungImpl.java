package org.haijun.study.cxf.impl;

import org.haijun.study.cxf.Baeldung;
import org.haijun.study.model.vo.UserVO;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "org.haijun.study.cxf.Baeldung")
public class BaeldungImpl implements Baeldung {

    private int counter;

    @Override
    public String hello(String name) {
        return "Hello " + name + "!";
    }

    @Override
    @WebMethod(exclude=true)// 排除
    public String register(UserVO user) {
        counter++;
        return user.getName() + " is registered student number " + counter;
    }
}
