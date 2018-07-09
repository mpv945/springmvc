package org.haijun.study.designPattern.xieweiType.interpreter.filterPattern;

import java.util.Arrays;
import java.util.List;

public class AndCriteria implements Criteria {

    /*private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }*/

    /**
     * 符合标准的接口方法
     *
     * @param persons
     * @return
     */
 /*   @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> firstCriteriaPersons = criteria.meetCriteria(persons);
        return otherCriteria.meetCriteria(firstCriteriaPersons);
    }*/

    /**
     * 使用可变参数介绍符合条件的标准接口
     */
    private List<Criteria> filters ;
    public AndCriteria(Criteria...filter) {
        this.filters = Arrays.asList(filter);
    }
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        for(Criteria filter : filters){
            persons = filter.meetCriteria(persons);
        }
        return persons;
    }

}
