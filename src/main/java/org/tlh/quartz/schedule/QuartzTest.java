package org.tlh.quartz.schedule;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.DateBuilder.evenMinuteDate;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
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

			Date runTime = evenMinuteDate(new Date());
			
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
			
			//添加到调度中
			scheduler.scheduleJob(job, trigger);
			
			
			job=newJob(HelloJob.class).withIdentity("job2","group1").build();
			trigger = newTrigger().withIdentity("trigger2", "group1").startNow()
			        .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();
			
			
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
