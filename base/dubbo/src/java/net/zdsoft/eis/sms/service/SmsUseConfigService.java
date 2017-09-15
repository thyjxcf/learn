package net.zdsoft.eis.sms.service;

/**
 * 类说明
 *
 * @author jiangf
 * @version 创建时间：2011-8-6 上午11:55:08
 */

public interface SmsUseConfigService {
	/**
	 * 判断指定单位的短信功能是否启用
	 * @param unitId 单位id
	 * @return 使用返回true；不使用则返回false
	 * @throws Exception
	 */
	public boolean isSmsUsed(String unitId) throws Exception;
	
	/**
	 * 通过单位id获取clientId
	 * @param unitId
	 * @return
	 */
	public String getClientId(String unitId);
}
