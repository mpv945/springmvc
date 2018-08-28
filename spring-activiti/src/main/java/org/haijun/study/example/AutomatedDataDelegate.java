package org.haijun.study.example;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;

public class AutomatedDataDelegate  implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        // 显示设置过程变量。在这种情况下，变量autoWelcomeTime与当前时间。
        Date now = new Date();
        delegateExecution.setVariable("autoWelcomeTime", now);
        //显示检索流程变量。
        System.out.println("Faux call to backend for ["
                + delegateExecution.getVariable("fullName") + "]");
    }
}
