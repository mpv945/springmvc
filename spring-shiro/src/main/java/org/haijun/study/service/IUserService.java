package org.haijun.study.service;

import org.haijun.study.model.po.User;

public interface IUserService {

    /**
     * 根据账号查询账号信息
     * @param usercode
     * @return
     */
    User findUserByUserCode(String usercode);


}
