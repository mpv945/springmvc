package org.haijun.study.lambda;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
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
    //userMap.values().stream().sorted(Comparator.comparing(User::getName, UserNameComparator.INSTANCE)).collect(Collectors.toList());
    //升序排序，流的写法
    //list.stream().sorted(Comparator.comparing(str -> str)).collect(Collectors.toList()).forEach(System.out::println);
    //升序排序，默认写法
        /*Collections.sort(list);
        System.out.println("list = " + list);*/
    //自定义排序器,默认写法
        /*Collections.sort(list, (item1, item2) -> item1.length() - item2.length()); //升序
        Collections.sort(list, (item1, item2) -> item2.length() - item1.length());   //降序
        System.out.println("list = " + list);*/
    //自定义排序器,Comparator写法
        /*Collections.sort(list, Comparator.comparingInt(str -> str.length()));                       //升序
        Collections.sort(list, Comparator.comparingInt(str -> str.length()).reversed());              //降序,会报错，因为编译器在这里无法推断str的类型为String，而是推断出事一个Objcet
        Collections.sort(list, Comparator.comparingInt((String str) -> str.length()).reversed());*/   //降序,显示的指定一个类型
    //自定义排序器,Comparator写法2
        /*Collections.sort(list, Comparator.comparingInt(String::length));           //升序
        Collections.sort(list, Comparator.comparingInt(String::length).reversed());  //降序
        System.out.println("list = " + list);*/

    //直接调用list的排序方法，Collections.sort()本质还是调用list.sort方法
        /*list.sort(Comparator.comparingInt(String::length));             //升序
        list.sort(Comparator.comparingInt(String::length).reversed());    //降序*/
    /**两层排序:先按照长度排序，再按照字符串顺序**/
    //Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));   //不区分大小写的排序
    //Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
    //Collections.sort(list, Comparator.comparingInt(String::length).thenComparing((item1, item2) -> item1.toLowerCase().compareTo(item2.toLowerCase())));
    //Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(Comparator.comparing(String::toLowerCase)));
    //Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(Comparator.comparing(String::toLowerCase, Comparator.reverseOrder())));

    //Collections.sort(list, Comparator.comparingInt(String::length).
    //thenComparing(Comparator.comparing(String::toLowerCase, Comparator.reverseOrder())).
    //thenComparing(Comparator.reverseOrder()));//和上一个结果是一样的，因为已经排好序了，最后一个就不起作用了
    // 多重排序
    //.sorted((person1, person2) -> person1.getName().compareTo(person2.getName()));

    //分页
    /*List<ReportMarketingCourseVo> result = resultTemp.entrySet().stream().map(obj -> obj.getValue()).
            //sorted(Comparator.comparing(obj -> obj.getId(),Comparator.reverseOrder()));
                    sorted((obja,objb)->objb.getId().compareTo(obja.getId())).
                    skip((pageNo-1)*pageSize).limit(pageNo*pageSize).collect(Collectors.toList());
            voPage.setRecords(result);
            voPage.setTotal(size);//总页数
            voPage.setSize(pageSize);//每页条数
            voPage.setCurrent(pageNo);//当前页*/

    // 遍历List<Map<String,Integer>>集合的元素
    //list.stream().forEach(map -> map.forEach((k,v) -> System.out.println("key:value = " + k + ":" + v)));
    public static void main(String[] args) {
        // 基本使用说明 https://my.oschina.net/lizaizhong/blog/1814267


        String[] str11 = {"08:00", "09:00", "10:00", "18:00", "21:00"};
        String[] str22 = {"12:00", "09:00", "10:00", "11:00"};
        String[] str33 = Stream.concat(Stream.of(str11), Stream.of(str22)) //合并
                .distinct()     //去重
                .sorted()       //排序
                .peek(System.out::println).toArray(String[]::new);
        // 额外记录reduce用法
        String[] temp = Stream.concat(Stream.of(str11), Stream.of(str22)) //合并
                .distinct()     //去重
                .sorted()       //排序
                .reduce((a, b) -> {     //拼接
                    a = a + "-" + b + "," + b;
                    return a;
                }).get().toString().split(",");
        //去掉垃圾数据
        str33 = Arrays.asList(temp)
                .stream()
                .filter(v -> v.contains("-"))
                //.peek(System.out::println)
                // 生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），新Stream每个元素被消费的时候都会执行给定的消费函数
                .peek(p -> {p = p.toUpperCase(); System.out.println(p);}) // 类似forEach，但比forEach灵活，可以做备份，不会破坏流的结构参与后期处理
                .toArray(String[]::new);
        System.out.println(Arrays.stream(str33).skip(1).collect(Collectors.joining("  "))+""+(5*0));
        // 各个参数用法以及说明 https://www.cnblogs.com/litaiqing/p/6026682.html
        //T reduce(T identity, BinaryOperator<T> accumulator); //identity：它允许用户提供一个循环计算的初始值。accumulator：计算的累加器，
        System.out.println("给定个初始值，求和");
        System.out.println(Stream.of(1, 2, 3, 4).reduce(100, (sum, item) -> sum + item));
        System.out.println(Stream.of(1, 2, 3, 4).reduce(100, Integer::sum));
        System.out.println("给定个初始值，求min");
        System.out.println(Stream.of(1, 2, 3, 4).reduce(100, (min, item) -> Math.min(min, item)));
        System.out.println(Stream.of(1, 2, 3, 4).reduce(100, Integer::min));
        System.out.println("给定个初始值，求max");
        System.out.println(Stream.of(1, 2, 3, 4).reduce(100, (max, item) -> Math.max(max, item)));
        System.out.println(Stream.of(1, 2, 3, 4).reduce(100, Integer::max));
        System.out.println("无初始值，求和");
        //注意返回值，上面的返回是T,泛型，传进去啥类型，返回就是啥类型。 下面的返回的则是Optional类型
        System.out.println(Stream.of(1, 2, 3, 4).reduce(Integer::sum).orElse(0));
        System.out.println("无初始值，求max");
        System.out.println(Stream.of(1, 2, 3, 4).reduce(Integer::max).orElse(0));
        System.out.println("无初始值，求min");
        System.out.println(Stream.of(1, 2, 3, 4).reduce(Integer::min).orElse(0));
        // 对对象的Code进行分组，然后对Count字段进行求和
        // Map<Integer, IntSummaryStatistics> collect = list.stream().collect(Collectors.groupingBy(Foo::getCode, Collectors.summarizingInt(Foo::getCount)));

        if(true){
            return;
        }
        // 初始化
        // 1. Individual values
        Stream<String> s = Stream.of("a", "b", "c");
        // 2. Arrays
        String [] strArray = new String[] {"a", "b", "c"};
        Stream<String> arrs = Stream.of(strArray);
        // 3. Collections
        List<String> list1 = Arrays.asList(strArray);
        Stream<String> lists = list1.stream();
        String joinStr = Stream.generate(() -> "test").limit(10).collect(Collectors.joining(","));
        System.out.println(joinStr);
        // 6. From Popular APIs
        String sentence = "Program creek is a Java site.";
        Stream<String> wordStream = Pattern.compile("\\W").splitAsStream(sentence);
        String[] wordArr = wordStream.toArray(String[]::new);
        System.out.println(Arrays.toString(wordArr));
        //Stream<LocalDate> date = Stream.iterate(LocalDate.now(), n->n.plusDays(1)).limit(20);

        //去除重复 distinct
        lists.distinct().forEach(p -> System.out.print(p + "\t"));  //1   2  3  follow wind   followwwind
        System.out.println();

        //过滤元素 filter
        lists.filter(p -> p.length() > 1).forEach(p -> System.out.print(p + "\t")); //follow  wind   followwwind
        System.out.println();

        // sorted 流排序,中间操作返回流本身
        lists.filter(str -> str.contains("w"))
                .sorted((str1, str2) -> {
                    if (str1.length() == str2.length()) {
                        return 0;
                    } else if (str1.length() > str2.length()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }).forEach(System.out::println);  //wind follow followwwind

        //limit 对一个Stream进行截断操作，获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素；
        lists.limit(5).forEach(p -> System.out.print(p + "\t")); //1  2  3  3  follow
        System.out.println();

        //skip 返回一个丢弃原Stream的前N个元素后剩下元素组成的新Stream，如果原Stream中包含的元素个数小于N，那么返回空Stream；
        lists.skip(5).forEach(p -> System.out.print(p + "\t")); //wind    followwwind
        System.out.println();

        //peek 生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例）
        // ，新Stream每个元素被消费的时候都会执行给定的消费函数；
        lists.peek(p -> {p = p.toUpperCase(); System.out.println(p);}).forEach(System.out::println);
        System.out.println();
        //转换元素 map
        lists.map(p -> p + "-->").forEach(System.out::print); // 1-->2-->3-->3-->follow-->wind-->followwwind-->
        lists.map(p -> p.split(" ")).map(p -> p[0] + "\t").forEach(System.out::print);//1 2  3  3  follow wind   followwwind
        lists.map(p -> p.split("")).map(p -> {
            String tmp = "";
            if(p.length > 1){
                tmp = p[1];
            }else{
                tmp = p[0];
            }
            return tmp + "\t";
        }).forEach(System.out::print); //1 2  3  3  o  i  o
        lists.filter(p -> p.matches("\\d+")).mapToInt(p -> Integer.valueOf(p)).forEach(p -> System.out.print(p + "\t"));//1   2  3  3
        //  // flatMap 和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中
        lists.flatMap(p -> Stream.of(p.split("www"))).forEach(p -> System.out.print(p + "\t"));////1     2 3  3  follow wind   follo  ind
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        outputStream.forEach(p -> System.out.print(p + "\t")); //1 2  3  4  5  6
        // 终端操作(Terminal) Stream
        //forEach
        lists.forEach(System.out::print);
        //match 流匹配,终结操作
        System.out.println(lists.allMatch(str -> str.length() == 3));// false
        System.out.println(lists.anyMatch(str -> str.length() > 5));// true
        System.out.println(lists.noneMatch(str -> str.length() > 5));// false
        //count
        System.out.println(lists.count());  //7
        //reduce
        Optional<String> reOptional = lists.reduce((str, str2) -> str + "-->" + str2);
        reOptional.ifPresent(System.out::println); //1-->2-->3-->3-->follow-->wind-->followwwind
        lists.filter(p -> p.matches("\\d+")).mapToInt(p -> Integer.valueOf(p)).reduce(Integer::sum).ifPresent(System.out::println);
        //collect
        lists.collect(Collectors.maxBy((p1, p2) -> p1.compareTo(p2))).ifPresent(System.out::println); //wind
        lists.collect(Collectors.minBy((p1, p2) -> p1.compareTo(p2))).ifPresent(System.out::println); //1
        int s11 = lists.filter(p -> p.matches("\\d+")).collect(Collectors.summingInt(p -> Integer.valueOf(p)));
        String liString = lists.collect(Collectors.joining(","));
        // //sum
        int sum = lists.filter(p -> p.matches("\\d+")).mapToInt(p -> Integer.valueOf(p)).sum(); //9

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n->n.add(BigInteger.ONE)).limit(1);//.collect(Collectors.toList());;
        integers.forEach(System.out::println);
        System.out.println("测试");
        String str = "1,2,3,4,10,11,9,66,222,12";
        List<Integer> list = Stream.of(str.split(","))
                .map(Integer::valueOf)
                .filter(x-> !Objects.equals(x,3))
                .sorted(Comparator.reverseOrder())
                .limit(4)
                .collect(Collectors.toList());

        List<String> strs = Arrays.asList("d", "b", "a", "c", "a");
        Optional<String> min = strs.stream().min(Comparator.comparing(Function.identity()));
        Optional<String> max = strs.stream().max((o1, o2) -> o1.compareTo(o2));
        System.out.println(String.format("min:%s; max:%s", min.get(), max.get()));// min:a; max:d
        Optional<String> aa = strs.stream().filter(obj -> !obj.equals("a")).findFirst();
        Optional<String> bb = strs.stream().filter(obj -> !obj.equals("a")).findAny();
        // 在串行的流中，findAny和findFirst返回的，都是第一个对象；而在并行的流中，
        // findAny返回的是最快处理完的那个线程的数据，所以说，在并行操作中，对数据没有顺序上的要求，那么findAny的效率会比findFirst要快的；
        Optional<String> aa1 = strs.parallelStream().filter(obj -> !obj.equals("a")).findFirst();
        Optional<String> bb1 = strs.parallelStream().filter(obj -> !obj.equals("a")).findAny();
        System.out.println(aa.get() + "===" + bb.get());// d===d
        System.out.println(aa1.get() + "===" + bb1.get());// d===b or d===c

        //1.第一种:通过collection集合提供的stream方法生成
        /*List<String> list = Arrays.asList("1","2","3","4");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::print);*/

        //2.第二种:通过Arrays提供的stream方法生成
        String[] s1 = new String[]{"1","2","3","4"};
        Stream<String> stream2 = Arrays.stream(s1);
        stream2.forEach(System.out::print);

        //3.第三种：利用steam的静态方法of
        Stream<String> stream3 = Stream.of("1","2","3","4");
        stream3.forEach(System.out::print);

        //4.第四种：创建无限流
        //选代流
        /*Stream<Integer> stream4 = Stream.iterate(0,(x)->x+2);
        stream4.forEach(System.out::print);*/

        //生成
        Stream<Double> stream5 = Stream.generate(Math::random);
        stream5.limit(5).forEach(System.out::print);
        // 字符串操作
        //jdk8时代，加个分隔符：
        //System.out.println(list.stream().collect(Collectors.joining(",")));
    }



}
