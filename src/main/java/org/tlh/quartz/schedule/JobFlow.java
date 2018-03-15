package org.tlh.quartz.schedule;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.tlh.quartz.job.Job1;
import org.tlh.quartz.listener.Job1ExecuteCompleteListener;

public class JobFlow {

	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail jobDetail = JobBuilder.newJob(Job1.class).withIdentity("job1", "group1").build();
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")//
					.startNow()//
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();
			
			//设置监听器--->监听指定的job 通过key进行匹配
			scheduler.getListenerManager().addJobListener(new Job1ExecuteCompleteListener(), KeyMatcher.keyEquals(jobDetail.getKey()));
			
			scheduler.scheduleJob(jobDetail, trigger);
			
			scheduler.start();
			
			try {
				Thread.sleep(60*1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			scheduler.shutdown(true);//等待所有任务执行完毕后关闭
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	//QuartzSchedulerThread核心类-->执行调度-->其run方法中每次会创建一个新的JobRunShell对象
	//JobRunShell--->run 方法复杂执行Job  --->其initialize方法会创建一个新的JobExecutionContextImpl对象(JobExecutionContext的实现类)
	
	
}
