package org.haijun.study.designPattern.xieweiType.interpreter.filterPattern;

import java.util.ArrayList;
import java.util.List;

public class CriteriaSingle implements Criteria {
    /**
     * 符合标准的接口方法（单身的人）
     *
     * @param persons
     * @return
     */
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> singlePersons = new ArrayList<>();
        for (Person person : persons) {
            if(person.getMaritalStatus().equalsIgnoreCase("SINGLE")){
                singlePersons.add(person);
            }
        }
        return singlePersons;
    }
}
