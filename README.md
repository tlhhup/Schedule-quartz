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