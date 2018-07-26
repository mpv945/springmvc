package org.haijun.study.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.haijun.study.model.TkTest;
import org.haijun.study.model.TkTestExample;
import tk.mybatis.mapper.common.Mapper;

public interface TkTestMapper extends Mapper<TkTest> {
    long countByExample(TkTestExample example);

    int deleteByExample(TkTestExample example);

    List<TkTest> selectByExample(TkTestExample example);

    int updateByExampleSelective(@Param("record") TkTest record, @Param("example") TkTestExample example);

    int updateByExample(@Param("record") TkTest record, @Param("example") TkTestExample example);
}