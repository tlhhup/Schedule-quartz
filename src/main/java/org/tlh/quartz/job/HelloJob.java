package org.tlh.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {
	
	private static Logger logger=LoggerFactory.getLogger(HelloJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.debug("jobKey---->"+context.getJobDetail().getKey());
		logger.debug("Trigger Key---->"+context.getTrigger().getKey());
		logger.debug("hello,Job--->"+new Date());
	}

}
