package net.zdsoft.eis.system.frame.timer;

import java.util.Calendar;

import net.zdsoft.eis.base.common.service.LoginLogService;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * 移动一个月前的登录日志到历史表
 * @author chens  2016-4-14下午1:31:52
 */
public class LoginLogMoveTimerTask extends QuartzJobBean {
 Logger log = LoggerFactory.getLogger(LoginLogMoveTimerTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LoginLogService loginLogService = (LoginLogService) ContainerManager
				.getComponent("loginLogService");

		log.debug("It's time to move login logs Begin----------");
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		//判断历史表是否存在
		boolean flag = loginLogService.isExistsTable(year);
		if(!flag){
			//不存在则创建历史表
			loginLogService.createTable(year);
		}
		//将一个月之前的数据移动到历史表，同时清除目前表数据
		loginLogService.insertIntoHistoryTable(year);
		
		log.debug("-----------move login logs end.");

		log.info("===========清理日志结束==================");
	}
	
}
