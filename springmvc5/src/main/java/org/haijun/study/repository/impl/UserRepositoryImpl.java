package org.haijun.study.repository.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;
import org.haijun.study.model.entity.User;
import org.haijun.study.model.querydsl.QUser;
import org.haijun.study.repository.UserRepositoryCustom;
import org.haijun.study.repository.comm.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl extends BaseDao implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

/*    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;*/

    /**
     * 查询用户课程信息
     *
     * @param conditionMap
     * @param pageable
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryUserCourse(Map<String, Object> conditionMap, Pageable pageable) {
        Map<String, Object> conditionMap1 = new HashMap<>();
        StringBuilder hql = new StringBuilder("from EntryPolicyEO P where 1=1 and P.status='1'");
/*        //经销商
        if(UserProfile.getCurrentUser() != null
                && StringUtil.isNotBlank(UserProfile.getCurrentUser().getDealerCode())){
            hql.append(" AND P.dealerCode in :dealerCode");
            conditionMap.put("dealerCode", StringUtil.splitToList(UserProfile.getCurrentUser().getDealerCode()));
        }
        //起止时间
        if(StringUtil.isNotBlank(requestMO.getStartDate())){
            hql.append(" AND P.createTime >= :startDate");
            conditionMap.put("startDate", DateUtil.stringToDate(requestMO.getStartDate(), DateStyle.YYYY_MM_DD));
        }*/
        hql.append(" AND P.businessType ='AUTO_GAP' ORDER BY updateTime desc nulls last,id desc");
        return this.findResultPageMapsBySql(hql.toString(), conditionMap,pageable);
    }





    /*@Override
    public User save(final User person) {
        em.persist(person);
        return person;
    }*/

    @Override
    public List<User> findPersonsByFirstnameQueryDSL(final String firstname) {
        final JPAQuery<User> query = new JPAQuery<>(em);
        final QUser person = QUser.user;

        return query.from(person).where(person.name.eq(firstname)).fetch();
    }

    @Override
    public List<User> findPersonsByFirstnameAndSurnameQueryDSL(final String firstname, final String surname) {
        final JPAQuery<User> query = new JPAQuery<>(em);
        final QUser person = QUser.user;

        return query.from(person).where(person.name.eq(firstname).and(person.createUser.eq(surname))).fetch();
    }

    @Override
    public List<User> findPersonsByFirstnameInDescendingOrderQueryDSL(final String firstname) {
        final JPAQuery<User> query = new JPAQuery<>(em);
        final QUser person = QUser.user;

        return query.from(person).where(person.name.eq(firstname)).orderBy(person.createUser.desc()).fetch();
    }

    @Override
    public int findMaxAge() {
        final JPAQuery<User> query = new JPAQuery<>(em);
        final QUser person = QUser.user;

        return query.from(person).select(person.age.max()).fetchFirst().intValue();
    }

    @Override
    public Map<String, Long> findMaxAgeByName() {
        final JPAQuery<User> query = new JPAQuery<>(em);
        final QUser person = QUser.user;

        return query.from(person).transform(GroupBy.groupBy(person.name).as(GroupBy.max(person.age)));
    }

}
