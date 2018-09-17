package org.haijun.study.repository;

import org.haijun.study.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 自定义接口
 */
public interface UserRepositoryCustom{

    /**
     * 查询用户课程信息
     * @return
     */
    Page<Map<String, Object>> queryUserCourse(Map<String, Object> conditionMap, Pageable pageable);


    /**
     * 为querydsl 新增示例
     */

    //User save(User person);

    List<User> findPersonsByFirstnameQueryDSL(String firstname);

    List<User> findPersonsByFirstnameAndSurnameQueryDSL(String firstname, String surname);

    List<User> findPersonsByFirstnameInDescendingOrderQueryDSL(String firstname);

    int findMaxAge();

    Map<String, Long> findMaxAgeByName();
}
