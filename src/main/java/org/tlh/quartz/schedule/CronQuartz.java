package org.tlh.quartz.schedule;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.tlh.quartz.job.HelloJob;

/**
 * 石英调度
 * @author tlh
 *
 */
public class CronQuartz {

	public static void main(String[] args) {
		try {
			//1.创建调度器
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			//2.创建任务
			JobDetail jobDetail = newJob(HelloJob.class).withIdentity("job1","group1").build();
			//3.创建触发器 秒 分 时 天 月 星期-->
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
			//4.添加到调度器中
			scheduler.scheduleJob(jobDetail,trigger);
			
			//5.开启调度
			scheduler.start();
			
			//6.休眠当前线程
			try {
				Thread.sleep(60*1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//关闭调度器
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
