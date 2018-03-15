package org.tlh.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job1 implements Job {
	
	private static Logger logger=LoggerFactory.getLogger(Job1.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//将数据存储在上下文中 所有的listener和job都可以获取
		context.put("Job1", "data");
		try {
			context.getScheduler().getContext().put("c1", "cData");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		logger.info("job1---->complete");
	}

}
