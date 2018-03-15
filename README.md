# Schedule-quartz
java调度

1. 使用流程
	1. 创建调度器
		1. 标准调度器：StdScheduler
		2. 远程调度器：RemoteScheduler	
	2. 创建任务--->实现job接口
	3. 创建触发器
	4. 添加到调度器
	5. 开启调度
	6. 关闭调度 
1. 石英调度
	1. 表达式：秒 分 时 天 月 周 年(可选)--->其取之范围及其含义查看`CronExpression`
	
			Field Name	 	Allowed Values	 	Allowed Special Characters
			Seconds	 				0-59	 			, - * /
			Minutes	 				0-59	 			, - * /
			Hours	 				0-23	 			, - * /
			Day-of-month	 		1-31	 			, - * ? / L W
			Month	 				0-11 or JAN-DEC	 	, - * /
			Day-of-Week	 			1-7 or SUN-SAT	 	, - * ? / L #
			Year (Optional)	 	empty, 1970-2199	 	, - * /
2. 在job中存储数据，可以通过`JobExecutionContext`执行上下文获取到JobDetail对象中的JobDataMap对象讲数据存在在该map数据中，但是需要添加注解

		@PersistJobDataAfterExecution//在执行期前更新JobDataMap中的数据
		@DisallowConcurrentExecution//确保没有多个实例
		public class ColorJob implements Job {
3. 任务监听器--->模拟任务流程

		public interface JobListener {
		
			//监听器的名称
		    String getName();
		    
			//(1)job将被执行时调用
		    void jobToBeExecuted(JobExecutionContext context);
		
			//(2)这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
		    void jobExecutionVetoed(JobExecutionContext context);
		    
			//(3)job执行完毕之后被调用，可在这个位置启动下一个任务
		    void jobWasExecuted(JobExecutionContext context,
		            JobExecutionException jobException);
	
		}	
4. 整个调度的执行流程
	1. QuartzSchedulerThread该类为核心对象，在其run方法中负责创建JobRunShell对象
	
			JobRunShell shell = null;
            try {
                shell = qsRsrcs.getJobRunShellFactory().createJobRunShell(bndle);
                shell.initialize(qs);
            } catch (SchedulerException se) {
                qsRsrcs.getJobStore().triggeredJobComplete(triggers.get(i), bndle.getJobDetail(), CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR);
                continue;
            }
	2. 执行JobRunShell
	
			 if (qsRsrcs.getThreadPool().runInThread(shell) == false) {
	            // this case should never happen, as it is indicative of the
	            // scheduler being shutdown or a bug in the thread pool or
	            // a thread pool being used concurrently - which the docs
	            // say not to do...
	            getLog().error("ThreadPool.runInThread() return false!");
	            qsRsrcs.getJobStore().triggeredJobComplete(triggers.get(i), bndle.getJobDetail(), CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR);
	        }
	3. JobRunShell的initialize方法
	
			public void initialize(QuartzScheduler sched)
	        throws SchedulerException {
	    		.....
					//创建新的JobExecutionContextImpl对象(JobExecutionContext的实现类)
	       	 	this.jec = new JobExecutionContextImpl(scheduler, firedTriggerBundle, job);
	   		 }
	4. JobRunShell的run方法执行Job
	
		    // execute the job
            try {
                log.debug("Calling execute on job " + jobDetail.getKey());
                job.execute(jec);
                endTime = System.currentTimeMillis();
     5. 通过执行流程的分析，通过JobExecutionContext设置的数据只有该job和其对应的listener可以获取