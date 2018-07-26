package org.haijun.study.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        // 第一种分页sqlSession.selectList("x.y.selectIf", null, new RowBounds(0, 10));
        PageHelper.startPage(1, 10);//这个分页必须紧跟查询，用offsetPage方法一样
        List<Test> ret = testMapper.selectByExample(te);
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(ret);
        //测试PageInfo全部属性
/*        //PageInfo包含了非常全面的分页属性
        assertEquals(1, page.getPageNum());
        assertEquals(10, page.getPageSize());
        assertEquals(1, page.getStartRow());
        assertEquals(10, page.getEndRow());
        assertEquals(183, page.getTotal());
        assertEquals(19, page.getPages());
        assertEquals(1, page.getFirstPage());
        assertEquals(8, page.getLastPage());
        assertEquals(true, page.isFirstPage());
        assertEquals(false, page.isLastPage());
        assertEquals(false, page.isHasPreviousPage());
        assertEquals(true, page.isHasNextPage());*/
        return ret;
    }

    /**
     * 通过Tk查询全部
     *
     * @return
     */
    @Override
    public List<TkTest> getTkAll() {
        Page<TkTest> page = PageHelper.startPage(1, 10).doSelectPage(()-> tkTestMapper.selectAll());
        return page.getResult();
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
