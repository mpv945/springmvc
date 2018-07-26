package org.haijun.study.service;

import org.haijun.study.entity.Test;
import org.haijun.study.entity.TkTest;

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

    /**
     * 通过Tk查询全部
     * @return
     */
    List<TkTest> getTkAll();

    /**
     * 添加
     * @param obj
     * @return
     */
    boolean add(TkTest obj);

    /**
     * 删除操作
     * @param id
     * @return
     */
    boolean remove(Long id);

    /**
     * 更新
     * @param id
     * @return
     */
    boolean update(Long id);

    /**
     *
     * @param id
     * @return
     */
    TkTest getById(Long id);
}
