package org.haijun.study.quartz.job;

import org.quartz.*;

import java.util.Date;

/**
 * 定义一个简单quartz.Job的实现，打印任务触发时间
 */
// Job并发 Job是有可能并发执行的，比如一个任务要执行10秒中，而调度算法是每秒中触发1次，那么就有可能多个任务被并发执行。、
// 有时候我们并不想任务并发执行，比如这个任务要去”获得数据库中所有未发送邮件的名单“，如果是并发执行，就需要一个数据库锁去避免一个数据被多次处理。这个时候一个
// @DisallowConcurrentExecution解决这个问题。
@DisallowConcurrentExecution//加上这个注释之后可以防止多个任务同时并发这种事情发生。
// 注意，@DisallowConcurrentExecution是对JobDetail实例生效，也就是如果你定义两个JobDetail，引用同一个Job类，是可以并发执行的。、
@PersistJobDataAfterExecution//每次执行完，JobDataMap都会被序列化，上次任务执行放入的值都会保存下来。
public class SimpleJob implements Job {

    private String name;

    @Override
    // Job.execute()方法是不允许抛出除JobExecutionException之外的所有异常的（包括RuntimeException)，
    // 所以编码的时候，最好是try-catch住所有的Throwable，小心处理。
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println(jec.getTrigger().getKey().getName()+" 该Job触发时间为"+new Date());
        //可以通过JobExecutionContext.isRecovering()查询任务是否是被恢复的。
        //jec.isRecovering();



        //  job.getJobDataMap().put("name", "quertz"); //加入属性name到JobDataMap
        // newJob().usingJobData("age", 18) //加入属性到age JobDataMap。可以设置多次
        // 对于JobDataMap 获取方式
        JobDetail detail = jec.getJobDetail();
        JobDataMap map = detail.getJobDataMap(); //方法一：获得JobDataMap
        JobDataMap jobdataMap = jec.getMergedJobDataMap();
        System.out.println("say hello to " + name + "[" + map.getInt("age") + "]" + " at "
                + new Date());
        // 由于零错误除以此作业将生成的异常的例外（仅在第一次运行）
        int flag = map.getInt("flag");
        JobKey jobKey = jec.getJobDetail().getKey();
        System.out.println("---" + jobKey + "，执行时间："
                 + ", flag： " + flag);
        try {
            int result = 4815 / flag;
        } catch (Exception e) {
            // 异常第一种，可以再次运行的
            System.out.println("--- Job1 出错!");
            // 修复分母，所以下次这个作业运行它不会再失败
            JobExecutionException e2 = new JobExecutionException(e);
            // @DisallowConcurrentExecution 保存JobDataMap修改的值
            map.put("flag", "1");
            // 这个工作会立即重新启动
            e2.setRefireImmediately(true);

            // 异常处理第二种
            // Quartz将自动取消与此作业相关联的所有触发器，以使其不再运行
            JobExecutionException e3 = new JobExecutionException(e);
            e2.setUnscheduleAllTriggers(true);
            throw e2;
        }
    }
    //方法二：属性的setter方法，会将JobDataMap的属性自动注入
    public void setName(String name) {
        this.name = name;
    }
}
