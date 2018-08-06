package org.haijun.study.service.impl;

import org.haijun.study.dao.PermissionMapper;
import org.haijun.study.dao.UserRoleMapper;
import org.haijun.study.model.dto.PermissionDO;
import org.haijun.study.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    /**
     * 查询用户的菜单信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<PermissionDO> findMenuListByUserId(Long userId) {
        List<Long> roles = userRoleMapper.findRolesByUserId(userId);
        if(CollectionUtils.isEmpty(roles)){
            return null;
        }
        return permissionMapper.findMenuListByRoles(roles,"menu");
    }

    /**
     * 查询用户的全部授权信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<PermissionDO> findPermissionListByUserId(Long userId) {
        List<Long> roles = userRoleMapper.findRolesByUserId(userId);
        if(CollectionUtils.isEmpty(roles)){
            return null;
        }
        return permissionMapper.findMenuListByRoles(roles,null);
    }
}
