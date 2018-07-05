package org.haijun.study.designPattern.structuralType.filterPattern;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrCriteria implements Criteria {

/*    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }
    *//**
     * 符合标准的接口方法
     *
     * @param persons
     * @return
     *//*
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> firstCriteriaItems = criteria.meetCriteria(persons);
        List<Person> otherCriteriaItems = otherCriteria.meetCriteria(persons);

        for (Person person : otherCriteriaItems) {
            if(!firstCriteriaItems.contains(person)){
                firstCriteriaItems.add(person);
            }
        }
        return firstCriteriaItems;
    }*/

    private List<Criteria> filters ;
    public OrCriteria(Criteria ...filter) {
        this.filters = Arrays.asList(filter);
    }
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        //List<Person> allData = Collections.synchronizedList(new ArrayList<>());
        List<Person> allData = new ArrayList<>();
        for(Criteria filter : filters){
            //persons = filter.meetCriteria(persons);
            List<Person> itemRet = filter.meetCriteria(persons);
            if(CollectionUtils.isEmpty(itemRet)){
                continue;
            }
            allData.addAll(itemRet);
            //Collections.addAll(allData,itemRet);
        }
        return allData.stream().parallel().distinct().collect(Collectors.toList());
    }
}
