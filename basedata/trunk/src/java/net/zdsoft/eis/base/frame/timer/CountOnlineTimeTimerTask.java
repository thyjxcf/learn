package net.zdsoft.eis.base.frame.timer;

import net.zdsoft.eis.base.data.service.CountOnlineTimeService;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CountOnlineTimeTimerTask extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		CountOnlineTimeService countOnlineTimeService = (CountOnlineTimeService)ContainerManager.getComponent("countOnlineTimeService");
		countOnlineTimeService.deleteByNullLogoutTime();
		System.out.println("----------删除成功----------------");
		
	}

}
