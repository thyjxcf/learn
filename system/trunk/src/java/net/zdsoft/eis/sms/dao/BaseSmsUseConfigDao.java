package net.zdsoft.eis.sms.dao;

import java.util.List;

import net.zdsoft.eis.sms.entity.SmsUseConfig;

/**
 *
 * @author jiangf
 * @version 创建时间：2011-8-5 下午03:15:30
 */

public interface BaseSmsUseConfigDao {
	/**
	 * 判断指定单位是否已经存在短信使用的配置信息
	 * 
	 * @param unitId
	 *            单位id
	 * @return 如果存在返回true；不存在则返回false
	 */
	public boolean ieExistsConfiginfo(String unitId);

	/**
	 * 通过单位id得到该单位的短信使用配置信息列表
	 * 
	 * @param unitId
	 *            单位id
	 * @return SmsUseConfig List
	 */
	public List<SmsUseConfig> getUseConfigByUnitId(String unitId);
	
	/**
	 * 通过单位id和特定内容的标志符（比如短信clientId），得到相应的配置
	 * @param unitId
	 * @param sign
	 * @return
	 */
	public SmsUseConfig getUseConfig(String unitId, String sign);
	
	/**
	 * 通过单位短信客户clientId得到单位Id
	 * @param clientId 短信客户clientId
	 * @return
	 */
	public String getUnitIdByClientId(String clientId);

	/**
	 * 保存指定单位指定配置信息
	 */
	public void addUseConfig(SmsUseConfig config);
	
	/**
	 * 修改指定单位指定配置信息
	 * @param config
	 */
	public void updateUseConfig(SmsUseConfig config);
	
	/**
	 * 判断指定的短信客户clientId，指定单位以外的其它单位是否已经保存在使用
	 * @param unitId 指定单位id
	 * @param clientId 短信客户帐号，目前为8位
	 * @return 如果指定单位以外的其他单位已经保存使用，返回已经使用单位的guid；
	 * 如果没有其他单位使用，则返回null
	 */
	public String isExistClientId(String unitId, String clientId);
	
	/**
	 * 根据单位id删除配置信息
	 * @param unitId
	 * @param sign
	 */
	public void deleteUseConfig(String unitId);
}
