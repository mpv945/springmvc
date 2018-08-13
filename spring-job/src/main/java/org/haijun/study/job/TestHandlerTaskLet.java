package org.haijun.study.job;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TestHandlerTaskLet implements Tasklet {


    /** 参考https://blog.csdn.net/qq_33366229/article/details/78711228
     https://docs.spring.io/spring-batch/trunk/reference/html/configureJob.html#txConfigForJobRepository
     https://blog.csdn.net/BuquTianya/article/details/52567165
     https://www.cnblogs.com/kevin443/p/6753703.html
     https://spring.io/projects/spring-batch
     */
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED; // 返回true和false 来表示成功或者失败
    }
}
