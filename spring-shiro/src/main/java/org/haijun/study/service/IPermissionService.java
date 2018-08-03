package org.haijun.study.service;

import org.haijun.study.model.dto.PermissionDO;

import java.util.List;

public interface IPermissionService {

    /**
     * 查询用户的菜单信息
     * @param userId
     * @return
     */
    List<PermissionDO> findMenuListByUserId(Long userId);

}
