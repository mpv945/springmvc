package org.haijun.study.lambda;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 分组GroupBy
 */
public class LambdaExample3 {

    // 参考  http://www.cnblogs.com/CarpenterLee/p/6550212.html
    public static void main(String[] args) {

        //list 转 map
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String,Long> result =
                items.stream().collect(
                        Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(result);

        //list转map  map 排序
        Map<String ,Long> finalMap = new LinkedHashMap<>();
        result.entrySet().stream()
                .sorted(Map.Entry.<String ,Long>comparingByValue()
                .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        System.out.println(finalMap);

/*        Map<Integer, String> result1 = list.stream().collect(
                Collectors.toMap(Hosting::getId, Hosting::getName));
        Map<Integer, String> result3 = list.stream().collect(
                Collectors.toMap(x -> x.getId(), x -> x.getName()));*/

        // 分组count条数
        // Map<String ,Long> counting = items.stream().collect(Collectors.groupingBy(Item::getName, Collectors.counting()));

        // 分组sun求和计算
        // Map<String ,Long> sum = items.stream().collect(Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));

        // 按 Price 分组，返回list<bean> 组
        // Map<BigDecimal, List<item>> groupByPriceMap = items.stream().collect(Collectors.groupingBy(Item::getPrice));

        // 按字段分组，list 自定义映射返回
        /*Map<BigDecimal, Set<string>> result =
                items.stream().collect(Collectors.groupingBy(Item::getPrice, Collectors.mapping(Item::getName, Collectors.toSet())));*/

        //filter使用
        //Stream map = persons.stream().filter(p -> p.getAge() > 18).map(person -> new Adult(person));

        // Collect(收集流的结果)
        // List adultList= persons.stream().filter(p -> p.getAge() > 18).map(person -> new Adult(person)).collect(Collectors.toList());

        //limit(截断)
        //对一个Stream进行截断操作，获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素

        //skip（跳过流前面的n个元素）
        //list.stream().skip(3).collect(Collectors.toList());

        // flatMap合并流
        String s = "a;c;d;f;r;g;h;j;k;l";
        List<String> lista = Arrays.asList(s).stream().map(S -> S.split(";")).flatMap(S -> Arrays.stream(S)).collect(Collectors.toList());
        System.out.println(lista);
        // 上面flatMap中的S是一个数组，进行flatMap操作可以将数组中的每个元素单独放到主Stream中，若不进行flatMap操作，则得到的是List<String[]>集合
        Stream<String> ret = lista.stream().map(line->line.split(" ")).flatMap(Arrays::stream);

        // 元素匹配
        // 判断list中是否有学生：
        // boolean result = list.stream().anyMatch(Person::isStudent);
        // 判断是否所有人都是学生：(noneMatch与allMatch恰恰相反，它用于判断流中的所有元素是否都不满足指定条件)
        // boolean result = list.stream().allMatch(Person::isStudent);
        // Optional<Person> person = list.stream().findAny();
    }
}
