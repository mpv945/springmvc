package org.haijun.study.designPattern.createType.singleton.threadLocal;

import java.util.concurrent.ThreadLocalRandom;

public class MyThreadLocalRandom {

    // ThreadLocalRandom是ThreadLocal和Random类的组合，它与当前线程隔离。
    // 因此，它通过简单地避免对 Random对象的任何并发访问，在多线程环境中实现了更好的性能
    // nextLong（）和nextDouble（）Java 8还添加了nextGaussian（）方法来生成下一个正态分布的值，其值与生成器序列的值相差 0.0和1.0。
    int unboundedRandomValue = ThreadLocalRandom.current().nextInt();//.nextInt(0, 100);请注意，0是包含下限，100是独家上限。


    // 每个线程需要有自己单独的实例;实例需要在多个方法中共享，但不希望被多线程共享
    // 不要将ThreadLocal与ExecutorService一起使用
    // 使用ThreadLocal的将产生不确定的结果-因为我们没有保证每个Runnable的给定动作用户id将通过同一个线程每次执行时间进行处理。
    // 我们可以使用withInitial（）静态方法并将供应商传递给它来构造ThreadLocal的实例
    // ThreadLocal 提供了线程本地的实例。它与普通变量的区别在于，每个使用该变量的线程都会初始化一个完全独立的实例副本。
    // ThreadLocal 变量通常被private static修饰。当一个线程结束时，它所使用的所有 ThreadLocal 相对的实例副本都可被回收。
    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);

    public static void set(int number){
        threadLocal.set(number);
    }

    public static int get(){
        if(threadLocal.get() != null){
            return threadLocal.get();
        }
        return 0;
    }

    public static void delete(){
        threadLocal.remove();
    }
}
