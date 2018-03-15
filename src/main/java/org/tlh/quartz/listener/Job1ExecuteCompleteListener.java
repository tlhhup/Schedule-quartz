package org.tlh.quartz.listener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlh.quartz.job.Job1;
import org.tlh.quartz.job.Job2;

public class Job1ExecuteCompleteListener implements JobListener {
	
	private static Logger logger=LoggerFactory.getLogger(Job1.class);

	@Override
	public String getName() {
		return "Job1ExecuteCompleteListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		//将被执行的时候调用
		logger.info("Job1ExecuteCompleteListener------->jobToBeExecuted");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		logger.info("Job1ExecuteCompleteListener------->jobExecutionVetoed");
	}

	//执行完了之后调用
	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		logger.info(context.get("Job1").toString());
		logger.info("Job1ExecuteCompleteListener------->jobWasExecuted");
		//继续执行下一个job
		JobDetail jobDetail = JobBuilder.newJob(Job2.class).withIdentity("job2", "group1").build();
		Trigger trigger = TriggerBuilder.newTrigger().startNow().withIdentity("trigger2","group1").startNow().build();
		
		try {
			context.getScheduler().scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	
}