package org.haijun.study.designPattern.structuralType.filterPattern;

import java.util.List;

/**
 * 制定标准（Criteria）；创建一个接口。
 */
public interface Criteria {

    /**
     * 符合标准的接口方法
     * @param persons
     * @return
     */
    public List<Person> meetCriteria(List<Person> persons);
}
