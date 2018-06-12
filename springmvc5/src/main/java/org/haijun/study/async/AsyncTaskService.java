package org.haijun.study.async;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Log4j2
@Service
// https://blog.csdn.net/v2sking/article/details/72795742
public class AsyncTaskService {
    // 通过@Async注解表明该方法是个异步方法，如果注解在类级别，则表明该类所有的方法都是异步方法，
    // 而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
    @Async
    public void task1(String name) throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(10000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task1任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    @Async
    public Future<String> task2() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(2000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task2任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
        return new AsyncResult<>("task2执行完毕");
    }
    @Async
    public Future<String> task3(String name) throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(3000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task3任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
        return new AsyncResult<String>("task3执行完毕");
    }
}
