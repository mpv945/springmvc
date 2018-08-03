package org.haijun.study.service.impl;

import org.haijun.study.dao.PermissionMapper;
import org.haijun.study.entity.Permission;
import org.haijun.study.model.dto.PermissionDO;
import org.haijun.study.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.List;

public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 查询用户的菜单信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<PermissionDO> findMenuListByUserId(Long userId) {


        return null;
    }
}
