package org.haijun.study.service;

import org.haijun.study.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {

    /**
     *  新增用户
     * @param user
     */
    void save(User user);

    /**
     * 查询全部用户列表
     * @return
     */
    List<User> list();

    /**
     * 分页查询
     * @return
     */
    Page<User> listPage();

    List<User> specification1();


}
