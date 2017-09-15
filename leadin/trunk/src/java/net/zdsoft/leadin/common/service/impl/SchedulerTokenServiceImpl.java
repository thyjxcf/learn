/**
 * 
 */
package net.zdsoft.leadin.common.service.impl;

import net.zdsoft.leadin.common.dao.SchedulerTokenDao;
import net.zdsoft.leadin.common.entity.SchedulerToken;
import net.zdsoft.leadin.common.service.SchedulerTokenService;

/**
 * @author zhaosf
 * 
 */
public class SchedulerTokenServiceImpl implements SchedulerTokenService {
	private SchedulerTokenDao schedulerTokenDao;

	public void setSchedulerTokenDao(SchedulerTokenDao schedulerTokenDao) {
		this.schedulerTokenDao = schedulerTokenDao;
	}

	@Override
	public void saveRunJob() {
		schedulerTokenDao.unlockForceToken();

	}

	public int getResetSecond(String ticketCode) {
		SchedulerToken token = schedulerTokenDao.getSchedulerToken(ticketCode);
		return token.getResetSecond();
	}

	@Override
	public void handleBusinessWithTicket(BusinessHandle bh) {
		String tokenCode = bh.getSchedulerTokenCode();

		while (true) {
			if (updateLockToken(tokenCode)) {
				bh.doThing();
				updateUnlockToken(tokenCode);
				break;
			} else {
				try {
					Thread.sleep(10000);// 10秒钟后轮询
				} catch (InterruptedException e) {

				}
			}

		}

	}

	public boolean updateLockToken(String tokenCode){
		return schedulerTokenDao.lockToken(tokenCode);
	}

	public void updateUnlockToken(String tokenCode){
		schedulerTokenDao.unlockToken(tokenCode);
	}
}
