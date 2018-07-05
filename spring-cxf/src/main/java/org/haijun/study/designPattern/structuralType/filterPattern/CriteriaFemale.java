package org.haijun.study.designPattern.structuralType.filterPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CriteriaFemale implements Criteria {
    /**
     * 符合标准的接口方法（符合女性标准的人）
     *
     * @param persons
     * @return
     */
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> femalePersons = new ArrayList<>();
        for (Person person : persons) {
            if(person.getGender().equalsIgnoreCase("FEMALE")){
                femalePersons.add(person);
            }
        }
        return femalePersons;
    }
}
