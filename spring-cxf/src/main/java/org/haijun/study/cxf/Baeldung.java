package org.haijun.study.cxf;

import org.haijun.study.vo.Student;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface Baeldung {

    @WebMethod
    String hello(@WebParam(name = "name") String name);

    @WebMethod// 可以通过参数控制不显示
    String register(@WebParam Student student);
}
