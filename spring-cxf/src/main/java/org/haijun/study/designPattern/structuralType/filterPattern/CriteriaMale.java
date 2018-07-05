package org.haijun.study.designPattern.structuralType.filterPattern;

import java.util.ArrayList;
import java.util.List;

public class CriteriaMale implements Criteria {
    // 符合标准（性别为MALE（男性）的人）
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> malePersons = new ArrayList<>();
        for (Person person : persons) {
            if(person.getGender().equalsIgnoreCase("MALE")){
                malePersons.add(person);
            }
        }
        return malePersons;
    }
}
