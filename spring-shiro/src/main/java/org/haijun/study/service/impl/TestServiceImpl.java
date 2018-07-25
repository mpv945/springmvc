package org.haijun.study.service.impl;

import org.haijun.study.dao.TestMapper;
import org.haijun.study.dao.TkTestMapper;
import org.haijun.study.entity.Test;
import org.haijun.study.entity.TestExample;
import org.haijun.study.entity.TkTest;
import org.haijun.study.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    private TestMapper testMapper;// 而项目代码启动类添加了@MapperScan

    @Autowired
    private TkTestMapper tkTestMapper;

    @Override
    public List<Test> getAll() {
        TestExample te = new TestExample();
        te.createCriteria().andAgeBetween(1,100);
        return testMapper.selectByExample(te);
    }

    /**
     * 通过Tk查询全部
     *
     * @return
     */
    @Override
    public List<TkTest> getTkAll() {
        return tkTestMapper.selectAll();
    }

    /**
     * 添加
     *
     * @param obj
     * @return
     */
    @Override
    public boolean add(TkTest obj) {
        return tkTestMapper.insert(obj)>0;
    }
}
