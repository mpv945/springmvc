package org.haijun.study.service;


import org.haijun.study.model.dto.UserDO;

public interface IUserService {

    /**
     * 根据账号查询账号信息
     * @param usercode
     * @return
     */
    UserDO findUserByUserCode(String usercode);


}
