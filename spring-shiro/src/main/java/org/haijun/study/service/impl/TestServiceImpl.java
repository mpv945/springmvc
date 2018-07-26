package org.haijun.study.service.impl;

import org.haijun.study.dao.TestMapper;
import org.haijun.study.dao.TkTestMapper;
import org.haijun.study.entity.Test;
import org.haijun.study.entity.TestExample;
import org.haijun.study.entity.TkTest;
import org.haijun.study.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
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
     * @param tkTest
     * @return
     */
    @Override
    // https://blog.csdn.net/whatlookingfor/article/details/51833378/ 或者https://www.cnblogs.com/coprince/p/5984816.html
    //@CachePut(value = "myCache",key="#id" ,condition = "#result")// 支持SpELhttps://blog.csdn.net/ya_1249463314/article/details/68484422
    public boolean add(TkTest tkTest) {
        return tkTestMapper.insert(tkTest)>0;
    }


    /**
     * 删除操作
     *
     * @param id
     * @return
     */
    @Override
    public boolean remove(Long id) {
        return false;
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @Override
    public boolean update(Long id) {
        return false;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public TkTest getById(Long id) {
        return null;
    }
}
