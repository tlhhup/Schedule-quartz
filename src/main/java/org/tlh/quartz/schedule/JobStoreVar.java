package org.tlh.quartz.schedule;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.tlh.quartz.job.ColorJob;

public class JobStoreVar {

	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail jobDetail = JobBuilder.newJob(ColorJob.class).withIdentity("job1", "group1").build();
			//设置数据
			jobDetail.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "red");
			jobDetail.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")//
					.startNow()//
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();
			
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
}
