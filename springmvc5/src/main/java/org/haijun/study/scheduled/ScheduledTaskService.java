package org.haijun.study.scheduled;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;

@Service
@Log4j2
public class ScheduledTaskService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000000) //通过@Scheduled声明该方法是计划任务，使用fixedRate属性每隔固定时间执行。单位毫秒
    public void reportCurrentTime() {
        log.info("每隔五秒执行一次 " + dateFormat.format(new Date()));
        //System.out.println("每隔五秒执行一次 " + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 28 11 ? * *"  ) //使用cron属性可按照指定时间执行，本例指的是每天11点28分执行；cron是UNIX和类UNIX(Linux)系统下的定时任务。
    public void fixTimeExecution(){
        log.info("在指定时间 " + dateFormat.format(new Date())+"执行");
    }
}
