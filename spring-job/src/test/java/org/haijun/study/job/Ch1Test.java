package org.haijun.study.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:book/job.xml")
public class Ch1Test {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("billJob")
    private Job job;

    /**
     * 使用Junit 运行
     * @throws Exception
     */
    @Test
    public void testRun() throws Exception{
        JobExecution result = jobLauncher.run(job ,new JobParameters());
        System.out.println(result.toString());
    }

    /**
     * 使用main函数运行
     * @param args
     */
    public static void main(String[] args) {
        // 初始化spring上下文
       ApplicationContext context = new ClassPathXmlApplicationContext("book/job.xml");
       // 获取作业调度器
        JobLauncher launcher = (JobLauncher)context.getBean("jobLauncher");
        // 获取任务对象
        Job job = (Job)context.getBean("billJob");
        try {
            // 通过作业调度器运行任务
            JobExecution result = launcher.run(job, new JobParameters());
            System.out.println(result.toString());

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }


}
