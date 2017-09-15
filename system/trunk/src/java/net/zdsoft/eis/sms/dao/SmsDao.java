package net.zdsoft.eis.sms.dao;

import java.util.List;

import net.zdsoft.eis.sms.entity.SmsQuery;
import net.zdsoft.keel.util.Pagination;

/**
 * 类说明
 * 
 * @author jiangf
 * @version 创建时间：2011-8-8 下午07:28:49
 */

public interface SmsDao {

	public List<SmsQuery> getSmsSendList(String unitId, String startDate,
			String endDate, Pagination page);

	/**
	 * 返回指定单位、指定时间段的部门的各种状态的短信统计值
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param unitId
	 *            单位unitId
	 * @return List,其内为 Id,Status,数量。
	 */
	public List getGroupCountByDep(String startDate, String endDate,
			String unitId);

	/**
	 * 返回指定单位、指定时间段的各种短信类型的各种状态的短信统计值
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param unitId
	 *            单位unitId
	 * @return List,其内为 Id,Status,数量。
	 */
	public List getGroupCountBySmsType(String startDate, String endDate,
			String unitId);

	/**
	 * 指定时间范围个人的按短信类别统计
	 * 
	 * @param startDate
	 * @param endDate
	 * @param userGuid
	 * @return
	 */
	public List getPersonalSmsTypeCount(String startDate, String endDate,
			String userGuid);

	/**
	 * 返回指定单位、指定时间段的发送用户的各种状态的短信统计值
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param unitId
	 *            单位unitId
	 * @return List,其内为 Id,Status,数量。
	 */
	public List getGroupCountByUser(String startDate, String endDate,
			String unitId);

	/**
	 * 得到指定用户、指定时间内指定条数的最新短信。用于信息主页
	 * 
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @param topItems
	 * @return
	 */
	public List getNewMsg(String userid, String startDate, String endDate,
			int topItems);
}
