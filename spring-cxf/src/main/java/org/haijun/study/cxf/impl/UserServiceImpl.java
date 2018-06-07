package org.haijun.study.cxf.impl;

import org.haijun.study.cxf.UserService;
import org.haijun.study.vo.User;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String name) {
        User u = new User();
        u.setUsername(name);
        u.setPassword("123456");
        return u;
    }

}
