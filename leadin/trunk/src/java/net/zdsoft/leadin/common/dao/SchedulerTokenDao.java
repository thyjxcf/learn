/**
 * 
 */
package net.zdsoft.leadin.common.dao;

import net.zdsoft.leadin.common.entity.SchedulerToken;

/**
 * @author zhaosf
 * 
 */
public interface SchedulerTokenDao {
	/**
	 * 获取重置分钟数
	 * 
	 * @param tokenCode
	 * @return
	 */
	public SchedulerToken getSchedulerToken(String tokenCode);

	/**
	 * 获取token
	 * 
	 * @param tokenCode
	 * @return
	 */
	public boolean lockToken(String tokenCode);

	/**
	 * 释放token
	 * 
	 * @param tokenCode
	 */
	public void unlockToken(String tokenCode);

	/**
	 * 强制释放token，用于在获取token后应用当掉的异常情况
	 */
	public void unlockForceToken();
}
