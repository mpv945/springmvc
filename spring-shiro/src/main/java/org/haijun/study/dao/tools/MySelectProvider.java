package org.haijun.study.dao.tools;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

// https://github.com/abel533/Mapper/wiki/5.extend(ExtendMapper 接口）
public class MySelectProvider extends MapperTemplate {

    public MySelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }
    /**
     * 自定义查询方法,和ExtendMapper 接口方法签名一致
     * @param ms
     * @return
     */
    public String dynamicSelect(MappedStatement ms) {
        // 首先是获取了当前接口的实体类型：
        final Class<?> entityClass = getEntityClass(ms);
        //因为接口返回值类型为 List<T>，MyBatis 会认为返回值类型为 List<Object>，这和我们想要的实体类型不一样，所以下一行代码就是设置返回值类型：
        //注意，只有返回 T 或者 List 时需要设置，返回 int 类型时不需要设置。
        setResultType(ms, entityClass);
        // 接下来就是纯粹的拼接 XML 形式的 SQL 了
        StringBuilder sql = new StringBuilder();
        // 拼接查询字段
        sql.append(SqlHelper.selectAllColumns(entityClass));
        // from tablename - 支持动态表名
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        // SqlHelper.whereAllIfColumns(entityClass,true);// 添加条件
        // order by xxx
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }

}
