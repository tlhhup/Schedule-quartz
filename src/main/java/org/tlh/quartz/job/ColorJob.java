package org.tlh.quartz.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取创建job中存储的变量
 * @author tlh
 *
 */
@PersistJobDataAfterExecution//在执行期前更新JobDataMap中的数据
@DisallowConcurrentExecution//确保没有多个实例
public class ColorJob implements Job {
	
	private static Logger logger=LoggerFactory.getLogger(ColorJob.class);

	public static final String FAVORITE_COLOR="favorite_color";
	public static final String EXECUTION_COUNT="execution_count";
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//1.获取存储的数据
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String color = dataMap.getString(FAVORITE_COLOR);
		int count = dataMap.getInt(EXECUTION_COUNT);
		logger.info(FAVORITE_COLOR+"--->"+color+"\t"+EXECUTION_COUNT+"--->"+count);
		//2.重新设置数据
		dataMap.put(EXECUTION_COUNT, ++count);
		logger.info("ColorJob--->"+new Date());
	}

}
