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