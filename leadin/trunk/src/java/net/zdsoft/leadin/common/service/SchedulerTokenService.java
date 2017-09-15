/**
 * 
 */
package net.zdsoft.leadin.common.service;

/**
 * @author zhaosf
 * 
 */
public interface SchedulerTokenService {
	/**
	 * 获取重置分钟数
	 * @param ticketCode
	 * @return
	 */
	public int getResetSecond(String ticketCode);
	

	/**
	 * 运行定时任务
	 */
	public void saveRunJob();

	/**
	 * 使用ticket处理业务
	 */
	public void handleBusinessWithTicket(BusinessHandle bh);

	/**
	 * 获取锁后要进行的业务处理
	 * 
	 * @author zhaosf
	 * 
	 */
	public interface BusinessHandle {
		/**
		 * 处理业务
		 */
		public void doThing();

		/**
		 * 获取ticket类型
		 * 
		 * @return
		 */
		public String getSchedulerTokenCode();
	}
	

	/**
	 * 获取token
	 * 
	 * @param tokenCode
	 * @return
	 */
	public boolean updateLockToken(String tokenCode);

	/**
	 * 释放token
	 * 
	 * @param tokenCode
	 */
	public void updateUnlockToken(String tokenCode);
}
