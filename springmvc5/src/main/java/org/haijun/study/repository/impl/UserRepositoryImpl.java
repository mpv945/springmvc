package org.haijun.study.repository.impl;

import org.haijun.study.repository.UserRepositoryCustom;
import org.haijun.study.repository.comm.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl extends BaseDao implements UserRepositoryCustom {

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

}
