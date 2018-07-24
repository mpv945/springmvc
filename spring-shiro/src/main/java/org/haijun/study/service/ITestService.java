package org.haijun.study.service;

import org.haijun.study.entity.Test;

import java.util.List;

/**
 * 测试服务接口
 */
public interface ITestService {

    /**
     * 查询全部
     * @return
     */
    List<Test> getAll();
}
