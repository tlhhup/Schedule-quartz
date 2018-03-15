package org.tlh.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.tlh.quartz.job.HelloJob;

public class QuartzTest {

	public static void main(String[] args) {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(HelloJob.class).withIdentity("job1",
					"group1").build();

			//Date runTime = evenMinuteDate(new Date());
			
			//Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
			
			//Trigger trigger = newTrigger().withIdentity("trigger3", "group1").startNow()
			 //       .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();
			//石英调度  表达式总共有6为  具体含义
		    CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/20 * * * * ?"))
		            .build();
			
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
			
			//开始
			scheduler.start();
			try {
				Thread.sleep(65*1000L);
			} catch (Exception e) {
			}

			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
