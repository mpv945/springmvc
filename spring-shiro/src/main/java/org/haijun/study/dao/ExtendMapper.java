package org.haijun.study.dao;


import org.apache.ibatis.annotations.SelectProvider;
import org.haijun.study.dao.tools.MySelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 扩展通用接口
 * @param <T>
 */
@RegisterMapper
public interface ExtendMapper<T> {

    /**
     * 自定义查询方法
     *
     * @return
     */
    @SelectProvider(type = MySelectProvider.class, method = "dynamicSQL")
    List<T> dynamicSelect();

}
