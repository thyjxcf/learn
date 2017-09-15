package net.zdsoft.eis.sms.dao;

import net.zdsoft.eis.sms.entity.SmsUseConfig;

/**
 * 类说明
 *
 * @author jiangf
 * @version 创建时间：2011-8-6 上午11:57:38
 */

public interface SmsUseConfigDao {
	/**
	 * 通过单位id和特定内容的标志符（比如短信clientId），得到相应的配置
	 * @param unitId
	 * @param sign
	 * @return
	 */
	public SmsUseConfig getUseConfig(String unitId, String sign); 
}
