package org.haijun.study.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串操作
 */
public class StringExample {

    // 取两字符串的交集
    public void jiaoji(){
        //List<Long> newIds = JSON.parseArray(("[3095139,9080109]"), Long.class);
        //List<Long> oldIds = JSON.parseArray(("[197868,197870]"), Long.class);
        //List<Long> missIds = oldIds.stream().filter(id -> !newIds.contains(id)).collect(Collectors.toList());
    }

    // 参考https://www.cnblogs.com/woodpecker/p/3537489.html
    public static void main(String[] args){
        String[] a = "1,2,3,4".split(",");//org.apache.commons.lang3.StringUtils.stripAll("1","2","3","4")
        //String[] b = stripAll(new String[]{"1","2","3","4"},"/");
        //System.out.println(strip("/123/123 /","/"));
        System.out.println(Arrays.toString(a));

        String start = "'";
        String end = "'";
        String link = ",";
        int[] arr = {1,2,3,4};
        String[] arr2 = {"1","2","3","4"};
        //forEach函数实现内部迭代
        StringBuilder sb = new StringBuilder();
        Arrays.asList(arr2).forEach(o -> { if (sb.length() > 0) {sb.append(link);} sb.append(start + o + end);});//forEach函数实现内部迭代
        System.out.println(sb);
        //map collect
        String str1 = Arrays.stream(arr).boxed().map(i -> start+i.toString()+end).collect(Collectors.joining(link));
        String str1_1 = Arrays.stream(arr2).map(i -> start+i.toString()+end).collect(Collectors.joining(link));
        System.out.println("str1:"+str1);
        System.out.println("str1_1:"+str1_1);
        //map reduce
        String str2 = Arrays.stream(arr).boxed().map(i -> start+i.toString()+end).reduce("/", String::concat);
        System.out.println("str2:"+str2);
        // 方法引用Object：：toString
        String str3 = Arrays.stream(arr).boxed().map(Object :: toString).reduce("/", String::concat);
        System.out.println("str3:"+str3);


        List<Person> list = new ArrayList<>(
                Arrays.asList(new Person("John",11,Person.Sex.MALE),
                        new Person("Robbin",22,Person.Sex.MALE),
                        new Person("Sarah",23,Person.Sex.FEMALE),
                        new Person("Amanda",23,Person.Sex.FEMALE))) ;
        //将年龄大于12的Person名字和性别拼接起来再用“，”隔开，结果用“[”“]”括起来
        System.out.println(list.parallelStream()
                .filter(p -> p.getAge() > 12)
                .map(p -> p.getName() + "_" + p.getGender())
                .collect(Collectors.joining(",","[","]")));
        //对性别为男的Person的年龄求和
        System.out.println(list.stream()
                .filter(p-> p.getGender() == Person.Sex.MALE)
                .mapToInt(obj -> obj.getAge()).sum());
        //对所有实例的年龄求和
        System.out.println(list.stream()
                .map(p->p.getAge())
                .reduce((x,y)->x+y).get());

    }


}
