package org.haijun.study.cxf;

import org.haijun.study.vo.User;

import javax.jws.WebService;

@WebService
public interface UserService {

    User getUser(String name);
}
