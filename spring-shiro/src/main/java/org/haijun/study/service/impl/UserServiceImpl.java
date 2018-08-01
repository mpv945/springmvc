package org.haijun.study.service.impl;

import org.haijun.study.dao.UserMapper;
import org.haijun.study.entity.User;
import org.haijun.study.model.dto.UserDO;
import org.haijun.study.service.IUserService;
import org.haijun.study.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;

/**
 * 用户管理服务
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据账号查询账号信息
     *
     * @param usercode
     * @return
     */
    @Override
    public UserDO findUserByUserCode(String usercode) {
        // 构建查询对象
        Weekend<User> wd = Weekend.of(User.class);
        wd.weekendCriteria().andEqualTo(User::getUsercode,usercode);
        User user = null;
        try {
            user = userMapper.selectOneByExample(wd);
        } catch (Exception e) {
            System.out.println("根据用户code查询用户信息异常{}"+e);
        }
        // 如果获取到，会返回单个对象，多个对象报错，没有查询到返回为空
        if(user != null){
            return BeanCopierUtils.convert(user, UserDO.class);
        }
        return null;
    }
}
