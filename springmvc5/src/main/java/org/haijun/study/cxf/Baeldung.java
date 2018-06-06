package org.haijun.study.cxf;

import org.haijun.study.model.vo.UserVO;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface Baeldung {

    String hello(@WebParam(name = "name") String name);
    String register(UserVO user);

}
