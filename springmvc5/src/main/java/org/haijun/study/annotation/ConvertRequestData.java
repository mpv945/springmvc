package org.haijun.study.annotation;

import java.lang.annotation.*;
// 自定义注解

// 1 .元注解：元注解就是定义注解的注解，包含@Target、@Retention、@Inherited、@Documented这四种。

// @Target：PACKAGE 注解作用于包，TYPE 注解作用于类型（类，接口，注解，枚举）；ANNOTATION_TYPE 注解作用于注解；CONSTRUCTOR 注解作用于构造方法
// METHOD 注解作用于方法；PARAMETER 注解作用于方法参数；FIELD 注解作用于属性；LOCAL_VARIABLE 注解作用于局部变量。默认可以作用于以上任何目标。
@Target(ElementType.METHOD)
// @Retention:描述注解的生命周期;RUNTIME 运行时保留，运行中可以处理;SOURCE 源码中保留，编译期可以处理;
// CLASS Class文件中保留，Class加载时可以处理 (默认RetentionPolicy.CLASS 值)
@Retention(RetentionPolicy.RUNTIME)
// 使用@Inherited修饰的注解作用于一个类，则该注解将被用于该类的子类。
@Inherited
// 比如有一个类A，在他上面有一个标记annotation，那么A的子类B是否不用再次标记annotation就可以继承得到
@Documented // 描述注解可以文档化，是一个标记注解。
public @interface ConvertRequestData {
    // 基本串类型
    String name() default "zhangsan";
    String address() default "";
    // 数组
    //int[] value();
    // 枚举类型
    //DateEnum week();// @ConvertRequestData(week=DateEnum.Sunday)
}
enum DateEnum {
    Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday
}
