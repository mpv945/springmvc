package org.haijun.study.designPattern.createType.singleton;

/**
 * 使用static代码块实现单例
 * 静态代码块中的代码在使用类的时候就已经执行了，所以可以应用静态代码块的这个特性的实现单例设计模式。
 */
public class MySingleton1 {

    private static MySingleton1 instance = null;

    private MySingleton1(){}

    static{
        instance = new MySingleton1();
    }

    public static MySingleton1 getInstance() {
        return instance;
    }

}
