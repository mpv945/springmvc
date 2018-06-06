package org.haijun.study.repository.comm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository("baseDao")
public class BaseDao {

    @PersistenceContext
    private EntityManager em;

    private static SessionFactory sf; // session工厂


    /**
     * 通过占位原生分页查询
     * @param sql sql
     * @param values 参数列表
     * @param pageRequest 分页数据
     * @return
     */
    protected Page<Map<String, Object>> findResultPageMapsBySql(String sql, Object[] values, Pageable pageRequest) {
        return this.findAllResultPageMapsBySql(sql, null, values, pageRequest);
    }

    /**
     *  命名参数查询
     * @param sql
     * @param conditionMap
     * @param pageRequest
     * @return
     */
    protected Page<Map<String, Object>> findResultPageMapsBySql(String sql, Map<String, Object> conditionMap, Pageable pageRequest) {
        return this.findAllResultPageMapsBySql(sql, conditionMap, null, pageRequest);
    }


    /**
     * 原生查询，？参数。不带分页
     * @param sql
     * @param values
     * @return
     */
    protected List<Map<String, Object>> findResultMapsBySql(String sql, Object[] values) {
        return this.findAllResultMapsBySql(sql, null, values);
    }

    /**
     * 原生查询，命名参数。不带分页
     * @param sql
     * @param conditionMap map参数信息
     * @return
     */
    protected List<Map<String, Object>> findResultMapsBySql(String sql, Map<String, Object> conditionMap) {
        return this.findAllResultMapsBySql(sql, conditionMap, null);
    }

    private List<Map<String, Object>> findAllResultMapsBySql(String sql, Map<String, Object> conditionMap, Object[] values) {
        if (sql != null) {
            Query query = this.em.createNativeQuery(sql);
            this.setParameters(query, conditionMap, values);
            // setResultTransformer 准备重新用心的方法实现，等官方版本 todo
            query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            return query.getResultList();
        } else {
            return null;
        }
    }

    // 分页查询
    private Page<Map<String, Object>> findAllResultPageMapsBySql(String sql, Map<String, Object> conditionMap, Object[] values, Pageable pageRequest) {
        if (sql != null && pageRequest.getOffset() >= 0 && pageRequest.getPageSize() > 0) {
            long total = this.findCount(sql, conditionMap, values);
            Query query = this.em.createQuery(sql);
            this.setParameters(query, conditionMap, values);
            query.setFirstResult(Integer.valueOf(pageRequest.getOffset()+""));// firstResult 起始记录
            query.setMaxResults(pageRequest.getPageSize()); // 每页显示的最大记录数
            //List<Object[]> content = query.getResultList();
            // setResultTransformer 准备重新用心的方法实现，等官方版本 todo
            query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map<String, Object>> content = query.getResultList();
            return new PageImpl(content, pageRequest, total);
        } else {
            return null;
        }
    }

    private long findCount(String hql, Map<String, Object> conditionMap, Object[] values) {
        String sql = this.hqlToSql(hql);
        if (conditionMap != null && !conditionMap.isEmpty()) {
            Object[] paras = this.mapToArray(hql, conditionMap);
            sql = this.improveSql(sql);
            return this.findCountBySql(sql, (Map)null, paras);
        } else {
            return this.findCountBySql(sql, (Map)null, values);
        }
    }

    private String improveSql(String sql) {
        sql = " " + sql + " ";
        String[] split = sql.split("[?]");

        for(int i = 0; i < split.length; ++i) {
            if (i != split.length - 1) {
                split[i] = split[i] + " ?" + (i + 1) + " ";
            }
        }

        return this.toStr(split);
    }
    private String toStr(Object[] param) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < param.length; ++i) {
            sb.append(param[i].toString());
        }

        return sb.toString();
    }

    private long findCountBySql(String sql, Map<String, Object> conditionMap, Object[] values) {
        if (sql != null) {
            String csql = sql.toUpperCase();
            int pos = csql.indexOf("ORDER BY");
            if (pos > -1) {
                csql = sql.substring(0, pos);
            } else {
                csql = sql;
            }

            Object o = this.findOneResutlObjectBySql("select count(0) from ( " + csql + " )", conditionMap, values);
            return ((BigDecimal)o).longValue();
        } else {
            return 0L;
        }
    }

    private Object findOneResutlObjectBySql(String sql, Map<String, Object> conditionMap, Object[] values) {
        if (sql != null) {
            Query query = this.em.createNativeQuery(sql);
            this.setParameters(query, conditionMap, values);
            return query.getSingleResult();
        } else {
            return null;
        }
    }

    private String hqlToSql(String hql) {
        if (StringUtils.isEmpty(hql)) {
            return hql;
        } else {
            SessionFactory sf = this.getSessionFactory();
            QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, Collections.EMPTY_MAP, (SessionFactoryImplementor) sf);
            queryTranslator.compile(Collections.EMPTY_MAP, true);
            String sql = queryTranslator.getSQLString();
            return sql;
        }
    }

    private Object[] mapToArray(String hql, Map<String, Object> conditionMap) {
        String tmp = " " + hql + " ";
        Object[] param = new Object[conditionMap.size()];
        int var5 = 0;

        while(true) {
            int start = tmp.indexOf(58);
            if (start < 0) {
                return param;
            }

            tmp = tmp.substring(start + 1);
            int end = tmp.indexOf(32);
            String key;
            if (end < 0) {
                key = tmp.substring(0, tmp.length());
            } else {
                key = tmp.substring(0, end);
            }

            int m = key.indexOf(41);
            if (m == -1) {
                m = key.indexOf(44);
            }

            if (m > 0) {
                key = key.substring(0, m);
            }

            param[var5++] = conditionMap.get(key);
        }
    }

    private void setParameters(Query query, Map<String, Object> map, Object[] values) {
        if (map != null && map.size() > 0) {
            Iterator var6 = map.entrySet().iterator();

            while(var6.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) var6.next();
                query.setParameter((String)entry.getKey(), entry.getValue());
            }
        } else if (null != values && values.length > 0) {
            for(int i = 0; i < values.length; ++i) {
                query.setParameter(i + 1, values[i]);
            }
        }

    }

    private SessionFactory getSessionFactory() {
        Session session = (Session)this.em.unwrap(Session.class);
        if (sf == null) {
            sf = session.getSessionFactory();
        }

        return sf;
    }

    public EntityManager getEm() {
        return em;
    }

    public static SessionFactory getSf() {
        return sf;
    }
}
