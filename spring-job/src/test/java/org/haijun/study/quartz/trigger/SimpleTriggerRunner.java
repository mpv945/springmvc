package org.haijun.study.quartz.trigger;

import org.haijun.study.quartz.job.SimpleJob;
import org.haijun.study.quartz.listener.MySchedulerListener;
import org.haijun.study.quartz.listener.MyTriggerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.CronScheduleBuilder.weeklyOnDayAndHourAndMinute;
import static org.quartz.DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule;
import static org.quartz.DateBuilder.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

//日期相关的builder
//import static org.quartz.DateBuilder.*;



/**
 * 集成到boot https://blog.csdn.net/tushuping/article/details/79636207
 * https://www.baeldung.com/introduction-to-spring-batch
 * https://www.baeldung.com/spring-quartz-schedule
 * https://hengboy.gitee.io/spring-boot-chapter-47/
 * 运行一个简单的触发器触发任务示例
 */
public class SimpleTriggerRunner {
    public static void main(String[] args) {
        simpleScheduleTest();
        //cronScheduleTest();

    }

    /**
     * cron表达式的触发器
     */
    public static void cronScheduleTest(){
        try {
            //从调度程序工厂获取一个调度程序的实例
            /*JobDetailFactoryBean 设置这个属性表示异常退出。重启
            <property name="requestsRecovery" value="true" />*/
            Scheduler scheduler  = StdSchedulerFactory.getDefaultScheduler();

            //显示调度程序的名称（这里会展示我们在quartz.properties文件中的名称）
            System.out.println("scheduleName = " + scheduler.getSchedulerName());

            String excludeExpression;
            // 这里设置禁用时间段为，每0-20之间，40-59之间不执行
            excludeExpression = "0-20,40-59 * * * * ?";
            CronCalendar cronCalendar = new CronCalendar(excludeExpression);

            // 标明要排除的日期 每天的17点10分
            scheduler.addCalendar("cronCalendar", cronCalendar, false, false); // 节假日加入schedule调度器

            JobDetail jobDetail = newJob(SimpleJob.class)// 指定jobDetail 绑定的Job实现对象
                    .withDescription("工作的描述")
                    .withIdentity("jobName2","group1")//jobDetail 工作名称和组名称
                    .build();

            // 设置监听器
            /*JobListener jobListener = new MyJobListener();
            Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
            scheduler.getListenerManager().addJobListener(jobListener, matcher);*/
            // 添加监听器
            SchedulerListener schedulerListener = new MySchedulerListener();
            scheduler.getListenerManager().addSchedulerListener(schedulerListener);



            CronTrigger trigger = newTrigger()
                    .withDescription("触发器的描述")
                    .withIdentity("trigger2", "group1")//指定trigger 名称和组名称

                    /** cron 表达式详细说明（由6个或者7个空格分隔的时间字符串表示。第七个为年，可选）
                     *      第1位置    表示秒     允许值0-59                      允许的特殊字符串 , - * /
                     *      第2位置    表示分钟   允许值0-59                      允许的特殊字符串 , - * /
                     *      第3位置    表示小时   允许值0-23                      允许的特殊字符串 , - * /
                     *      第4位置    表示日期   允许值1-31                      允许的特殊字符串 , - * ? / L W C
                     *      第5位置    表示月份   允许值1-12                      允许的特殊字符串 , - * /
                     *      第6位置    表示星期   允许值1-7                       允许的特殊字符串 , - * ? / L C #
                     *      第7位置    表示年份   允许值空或者1970-2099（不必须） 允许的特殊字符串  , - * /
                     *  特殊字符说明
                     *      星号（*）：表示对应时间域的每一个时刻，例如*在分钟时刻，表示每分钟
                     *      问号（?）：只有日期和星期字段可以使用，他通常为无意义的值，相当于占位符
                     *      减号（-）：表达一个范围，10-12 表示10，11，12
                     *      逗号（,）：表示一个列表值 例如在星期域，MON,WED,FDI 则表示星期1，星期三和星期五
                     *      斜杆（/）：x/y 表示步长。0/15={0,15,30,45};5/15={5，20，35，50}；*斜杠/y 等价0/y
                     *      [N]L     ：该字段只能在日期共和星期使用，在日期域表示这个月最后一天，在星期里表示星期六（对应7），这星期六表示星期最后一天
                     *                      如果L前面带数字，6L在星期表示最后一个星期五。在日期表示这个月最后第N天
                     *      [N]W     ：该字段只能出现在日期里，表示距离该月N日最近的工作日，15W表示匹配这个月15号最近工作日；如果15号是工作日就直接使用
                     *                      如果15号是星期六。则匹配14号星期五，如果15是星期天，则匹配16号星期一。但是匹配的日期时间范围不能跨月lW是星期六。会匹配到3号。因为不能跨月
                     *      LW组合   ：在日期字段可以组合LW使用，表示当月的最后一个工作日
                     *      井号（#）：只能在星期里面使用。表示当月的某个工作日，例如4#5 表示当月第5个星期三，如果没有则忽略不触发
                     *      [N]C     ：该字段只能在日期共和星期使用，例如日期5C在日期，表示5日以后的那一天，1C在星期表示星期日后的第一天
                     *  corn对特殊字符大小写不敏感，对星期英文缩写也不敏感，下面给出一些简单示例】
                     *  0 0 12 * * ? 每天12点      0 15 10 ? * * 每天10点15分      0 15 10 * * ? 每天10点15分      0 15 10 * * ? * 每天10点15分，最后一个*可以指定年份
                     *  0 * 14 * * ? 每天14点开始每分钟运行一次，结束于14点59      0 0/5 14 * * ? 每天14:00开始间隔5分钟跑一次。结束于14点55，最后不能超过时间字段定义的14
                     *  0 0/5 14,18 * * ? 每天两个时间点14:00 ,18:00 开始，每隔5分钟跑一次，不能超过14:59 ,18:59.所以14:55 ,18:55 以后在间隔5分钟就失效了
                     *  0 0-5 15 * * ? 每天14：00 到14：05 每分钟运行一次          0 10,44 14 ? 3 WED  3月每周三14:10到14:44 ，每分钟运行一次
                     *  0 15 10 ? MON-FRI 每周一到周五的10:15 分运行               0 15 10 15 * ? 每月15日10点15分运行
                     *  0 15 10 L * ? 每月最后一天的10点15分运行                   0 15 10 ? * 6L 每月最后一个星期五的10点15分运行
                     *  0 15 10 ? * 6L 2018-2020 表示2018到2020每月最后星期五运行  0 15 10 ? * 6#3 每月第三个星期五的10点15分运行
                     */
                    .withSchedule(cronSchedule("0/10 * * * * ?"))// cronSchedule 静态方法根据表达式字符串生成cron实例.每10秒触发一次
                    //.startAt(new Date())//不设置，默认为当前时间
                    //.endAt()//触发器结束时间，和开始时间可以组成执行范围
                    .modifiedByCalendar("cronCalendar")// 更新Calendar
                    .build();
            // 设置监听器
            TriggerListener triggerListener = new MyTriggerListener();
            Matcher<TriggerKey> matcher = KeyMatcher.keyEquals(trigger.getKey());
            scheduler.getListenerManager().addTriggerListener(triggerListener, matcher);
            // 这里的时间是根据corn表达式算出来的值。计算一个时间内，要触发的时间点
            trigger.getNextFireTime();
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression("0/10 * * * * ?");
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);// 把统计的区间段设置为从现在到1月后的今天（主要是为了方法通用考虑)
            // 第一个参数为cron表达式对象，第二个为排除或者包含的Calendar，每月为null，要计算的开始时间，要结算的结束时间
            List<Date> dates = TriggerUtils.computeFireTimesBetween(cronTriggerImpl, cronCalendar, now, calendar.getTime());
            System.out.println(dates.size());
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            for (Date date : dates) {
                System.out.println(dateFormat.format(date));
            }
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

            //运行一段时间后关闭
            Thread.sleep(10000);
            scheduler.shutdown(true);

            System.out.println("------- 关闭调度器 -----------------");
            SchedulerMetaData metaData = scheduler.getMetaData();
            System.out.println("~~~~~~~~~~  执行了 " + metaData.getNumberOfJobsExecuted() + " 个 jobs.");

        } catch (SchedulerException | ParseException | InterruptedException e ) {
            e.printStackTrace();
        }
    }

    /**
     * cron表达式的触发器(不能跳过开始时间) 它的作用是在于补充Trigger的时间。可以排除或加入某一些特定的时间点。
     * Quartz体贴地为我们提供以下几种Calendar，注意，所有的Calendar既可以是排除，也可以是包含
     *      HolidayCalendar。指定特定的日期，比如20140613。精度到天。
     *      DailyCalendar。指定每天的时间段（rangeStartingTime, rangeEndingTime)，格式是HH:MM[:SS[:mmm]]。也就是最大精度可以到毫秒。
     *      WeeklyCalendar。指定每星期的星期几，可选值比如为java.util.Calendar.SUNDAY。精度是天。
     *      MonthlyCalendar。指定每月的几号。可选值为1-31。精度是天
     *      AnnualCalendar。 指定每年的哪一天。使用方式如上例。精度是天。
     *      CronCalendar。指定Cron表达式。精度取决于Cron表达式，也就是最大精度可以到秒。
     *      具体参考https://blog.csdn.net/yangshangwei/article/details/78174050
     */
    public static BaseCalendar annualCalendarTest(){

        // 声明一个节假日 holidays，标明要排除的日期
        // 法定节日是以每年为周期的，所以使用AnnualCalendar而不是HolidayCalendar
        AnnualCalendar holidays = new AnnualCalendar();//法定节日是以年为周期，所以使用年时间

        Calendar wuyi = new GregorianCalendar();// 定义五一劳动节
        wuyi.add(Calendar.MONTH ,5);
        wuyi.add(Calendar.DATE , 1);

        // 下一个15秒
        Date startTime = nextGivenSecondDate(null, 15);

        Calendar guoqing = new GregorianCalendar();// 定义十一国庆节
        guoqing.add(Calendar.MONTH ,10);
        guoqing.add(Calendar.DATE , 1);

        Calendar myCoustm = new GregorianCalendar();// 定义自定义过滤的时间
        myCoustm.setTime(newDate().inMonthOnDay(9, 25).build());

        ArrayList<Calendar> calendars = new ArrayList<>();
        calendars.add(wuyi);
        calendars.add(guoqing);
        calendars.add(new GregorianCalendar(2018,9,9));
        holidays.setDayExcluded(myCoustm,true);//单个设置排除
        //holidays.setDaysExcluded(calendars);//排除这两个日期

        return holidays;
    }

    // Trigger实现类
    public void testTrigger(){
        // Quartz有以下几种Trigger实现:
        // 1  .  SimpleTrigger  指定从某一个时间开始，以一定的时间间隔（单位是毫秒）执行的任务。它适合的任务类似于：9:00 开始，每隔1小时，执行一次。
        simpleSchedule()
                .withIntervalInHours(1) //每小时执行一次 //.withIntervalInMinutes(1) //每分钟执行一次
                .repeatForever() //次数不限 // .withRepeatCount(10) //次数为10次
                .build();

        //2   . CalendarIntervalTrigger, 不要指定具体的间隔时间，可以指定在一个相对间隔时候执行，CalendarIntervalTrigger支持的间隔单位有秒，分钟，小时，天，月，年，星期。
        calendarIntervalSchedule()
                .withIntervalInDays(1) //每天执行一次// .withIntervalInWeeks(1) //每周执行一次
                //.withInterval(1,DateBuilder.IntervalUnit.DAY) // 第二种通过数字加IntervalUnit类型
                .build();
        // 3 .  DailyTimeIntervalTrigger 指定每天的某个时间段内，以一定的时间间隔执行任务。并且它可以支持指定星期。
        dailyTimeIntervalSchedule()
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0)) //第天9：00开始 //startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0)) //第天9：00开始
                .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(16, 0)) //16：00 结束 //.endingDailyAfterCount(10) //每天执行10次，这个方法实际上根据 startTimeOfDay+interval*count 算出 endTimeOfDay
                .onDaysOfTheWeek(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY) //周一至周五执行 //onDaysOfTheWeek(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY) //周一至周五执行
                .withIntervalInHours(1) //每间隔1小时执行一次 //withIntervalInHours(1) //每间隔1小时执行一次
                .withRepeatCount(100) //最多重复100次（实际执行100+1次）
                .build();
        // 4 .  CronTrigger 适合于更复杂的任务，它支持类型于Linux Cron的语法（并且更强大）。基本上它覆盖了以上三个Trigger的绝大部分能力（但不是全部）—— 当然，也更难理解。
        cronSchedule("0 30 9 ? * MON") // 每周一，9:30执行一次
                .build();
        weeklyOnDayAndHourAndMinute(MONDAY,9, 30) //等同于 0 30 9 ? * MON
                .build();
    }
    /**
     * 简单触发器运行
     */
    public static void simpleScheduleTest(){
        try {
            //从调度程序工厂获取一个调度程序的实例
            Scheduler scheduler  = StdSchedulerFactory.getDefaultScheduler();

            //显示调度程序的名称（这里会展示我们在quartz.properties文件中的名称）
            System.out.println("scheduleName = " + scheduler.getSchedulerName());

            // 添加calendar，排除时间
            BaseCalendar holidays = annualCalendarTest();
            scheduler.addCalendar("holidays", holidays, false, false); // 节假日加入schedule调度器

            // JobDetailImpl 过期。新的使用JobBuilder.newJob来创建
            //JobDetail jobDetail = new JobDetailImpl("","",SimpleJob.class);
            /** 重要:
             *  定义一个job，并绑定到我们自定义的HelloJob的class对象
             *  这里并不会马上创建一个HelloJob实例，实例创建是在scheduler安排任务触发执行时创建的
             *  这种机制也为后面使用Spring集成提供了便利
             */
            JobDetail jobDetail = newJob(SimpleJob.class).// 指定jobDetail 绑定的Job实现对象
                    withIdentity("jobName1","group1")//jobDetail 名称和组名称
                    .usingJobData("name", "quartz") //定义属性
                    .build();

            // 声明一个触发器，现在就执行(schedule.start()方法开始调用的时候执行)；并且每间隔2秒就执行一次
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")//指定trigger 名称和组名称
                    .startNow()
                    .modifiedByCalendar("holidays")//更新Calendar
                    .withSchedule(simpleSchedule()//使用SimpleTrigger
                                    .withIntervalInSeconds(2)//每隔2秒执行一次
                                    .withRepeatCount(20)//运行次数。
                            //.repeatForever() //指定触发器将无限期重复,一直执行，奔腾到老不停歇
                    )
                    .build();
            //TriggerUtils 提供对Trigger计算能力
            //Trigger trigger1 = TriggerUtils.computeEndTimeToAllowParticularNumberOfFirings()
            // 注册。告诉quartz使用定义的触发器trigger安排执行任务job
            scheduler.scheduleJob(jobDetail, trigger);

            //启动任务调度程序,内部机制是线程的启动
            scheduler.start();

            //关闭任务调度程序,如果不关闭，调度程序schedule会一直运行着
            //scheduler.shutdown();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 时间段的指定
    public static BaseCalendar dailyCalendarTest(){
        DailyCalendar dailyCalendar = new DailyCalendar("12:17:30", "12:18:20");
        dailyCalendar.setInvertTimeRange(true); // 时间反转，为true表示只有这次时间段才会被执行，为false表示排除这时间段
        return  dailyCalendar;
    }

    // 重启Job
    public void restartJob(){
        //从调度程序工厂获取一个调度程序的实例
        try {
            Scheduler scheduler  = StdSchedulerFactory.getDefaultScheduler();
            List<String> triggerGroupNames =  scheduler.getTriggerGroupNames();//获取调度器中所有的触发器组
            for(String item : triggerGroupNames){
                Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(item));
                for(TriggerKey key : triggerKeys){
                    //Trigger trigger = scheduler.getTrigger(key);
                    scheduler.resetTriggerFromErrorState(key);
                    scheduler.rescheduleJob(key,newTrigger().withIdentity("sdfsd","dfs").build());
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
