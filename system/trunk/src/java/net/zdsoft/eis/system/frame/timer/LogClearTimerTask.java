package net.zdsoft.eis.system.frame.timer;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import net.zdsoft.eis.base.common.service.UserLogService;
import net.zdsoft.eis.system.frame.dao.LogConfigDao;
import net.zdsoft.eis.system.frame.entity.LogConfig;
import net.zdsoft.keelcnet.config.ContainerManager;

public class LogClearTimerTask extends QuartzJobBean {
 Logger log = LoggerFactory.getLogger(LogClearTimerTask.class);

	//	LogClearAction logClear = new LogClearAction();

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LogConfigDao logConfigDao = (LogConfigDao) ContainerManager
				.getComponent("logConfigDao");
		UserLogService userLogService = (UserLogService) ContainerManager
				.getComponent("userLogService");
		List<LogConfig> configList = logConfigDao.getLogConfigs();

		//只清理当前安装和启用的子系统的日志信息
		//		List<LogConfig> configList = logConfigService.getLogConfigs(
		//				getLoginInfo().getUnitClass(), getLoginInfo().getUnitType());
		LogConfig logConfigDto;
		log.debug("It's time to clear logs;Begin----------");
		for (int i = 0; i < configList.size(); i++) {
			logConfigDto = configList.get(i);
			if (logConfigDto.getDays() > 0) {
				log.debug("clear logs where SubSystem in"
						+ logConfigDto.getSubSystem() + " and logTime before "
						+ logConfigDto.getDays());
				try {
					userLogService.deleteUserLogs(logConfigDto.getSubSystem(),
							logConfigDto.getDays());
				} catch (Exception e) {
					log.error(e.toString());
				}
			}
		}
		log.debug("-----------clear logs end.");

		log.info("===========清理日志开始==================");
	}

}
