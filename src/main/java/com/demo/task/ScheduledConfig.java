package com.demo.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;


/**
 * 多线程执行定时任务
 * 所有的定时任务都放在一个线程池中，定时任务启动时使用不同线程。
 * @author allenpeng
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        //设定一个长度10的定时任务线程池
        registrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}
