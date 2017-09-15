package net.zdsoft.eis.sms.service;

import java.util.List;

import net.zdsoft.eis.sms.entity.SmsQuery;
import net.zdsoft.eis.sms.entity.SmsStat;
import net.zdsoft.keel.util.Pagination;

/**
 * 
 * @author jiangf
 * @version 创建时间：2011-8-8 下午07:28:25
 */

public interface SmsService {

	/** 获取发送短信的记录 */
	public List<SmsQuery> getSmsSendList(String unitId, String startDate,
			String endDate, Pagination page);

	/** 指定时间范围的按部门统计 */
	public List<SmsStat> getDepGroupCount(String startDate, String endDate,
			String unitId);

	/** 指定时间范围的按短信类别统计 */
	public List<SmsStat> getSmsTypeGroupCount(String startDate, String endDate,
			String unitId);

	/**
	 * 指定时间范围的按发送人统计
	 */
	public List<SmsStat> getUsersGroupCount(String startDate, String endDate,
			String unitId);

	/**
	 * 指定时间范围个人的按短信类别统计
	 * 
	 * @param startDate
	 * @param endDate
	 * @param userGuid
	 * @return
	 */
	public List<SmsStat> getPersonalSmsTypeCount(String startDate,
			String endDate, String userId);
}
