package org.haijun.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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


}
