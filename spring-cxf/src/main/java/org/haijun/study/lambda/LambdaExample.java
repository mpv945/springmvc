package org.haijun.study.lambda;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * jdk8 Lambda 实例
 */
public class LambdaExample {
/*  if(! CollectionUtils.isEmpty(records)){
        // 去重复
        List<Long> classifyIds = records.stream().map(Course::getClassifyId).distinct().collect(Collectors.toList());
        List<Classify> classifies = iClassifyService.selectBatchIds(classifyIds);
        Map<Long, String> result1 = classifies.stream()
                //.sorted(Comparator.comparingLong(Classify::getId).reversed()) // 倒序
                .collect(Collectors.toMap(
                        Classify::getId, Classify::getName //Function.identity()可以作为实体对象
                        //,(oldValue, newValue) -> oldValue// 重复的键，将以先出现的，(oldValue, newValue) -> newvalue 最后出现的
                        //,LinkedHashMap::new// 有序的map
                ));
        records.forEach(item ->{ item.setClassifyName(result1.get(item.getClassifyId())); });
        page.setRecords(records);
    }*/

    // forEach + Lambda表达式操作list，操作map = items.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
    //items.forEach(item->{if("C".equals(item)){ System.out.println(item); } });

    // 排序
    // userMap.values().stream().sorted(Comparator.comparing(User::getName, UserNameComparator.INSTANCE)).collect(Collectors.toList());
    // 多重排序
    //.sorted((person1, person2) -> person1.getName().compareTo(person2.getName()));

    // 遍历List<Map<String,Integer>>集合的元素
    //list.stream().forEach(map -> map.forEach((k,v) -> System.out.println("key:value = " + k + ":" + v)));
    public static void main(String[] args) {
        String str = "1,2,3,4,10,11,9,66,222,12";
        List<Integer> list = Stream.of(str.split(","))
                .map(Integer::valueOf)
                .filter(x-> !Objects.equals(x,3))
                .sorted(Comparator.reverseOrder())
                .limit(4)
                .collect(Collectors.toList());

        // 字符串操作
        //jdk8时代，加个分隔符：
        //System.out.println(list.stream().collect(Collectors.joining(",")));
    }



}
