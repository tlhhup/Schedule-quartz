package org.tlh.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job2 implements Job {
	
	private static Logger logger=LoggerFactory.getLogger(Job2.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//该方法获取到的数据为null原因为JobExecutionContext不一致
		context.get("Job1");
		
		try {
			logger.info("c1---->"+context.getScheduler().getContext().getString("c1"));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		logger.info("job2---->complete");
	}

}
