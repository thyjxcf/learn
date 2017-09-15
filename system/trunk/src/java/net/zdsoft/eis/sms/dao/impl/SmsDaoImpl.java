package net.zdsoft.eis.sms.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.eis.sms.dao.SmsDao;
import net.zdsoft.eis.sms.entity.SmsQuery;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

/**
 *
 * @author jiangf
 * @version 创建时间：2011-8-8 下午07:29:02
 */

public class SmsDaoImpl extends BaseDao<BaseEntity> implements SmsDao{
	
	@Override
	public BaseEntity setField(ResultSet rs) throws SQLException {
		return null;
	}
	
	public List<SmsQuery> getSmsSendList(String unitId,String startDate, String endDate,Pagination page){
		StringBuffer hsql = new StringBuffer();
		hsql.append(" SELECT a.* ,b.mobile,b.receiver_name FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND a.unit_Id = b.unit_Id AND a.unit_Id = '");
		hsql.append(unitId);
		hsql.append("' and send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' order by send_date ,send_hour,send_minutes");
		return query(hsql.toString(), new MultiRowMapper<SmsQuery>(){

			@Override
			public SmsQuery mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SmsQuery smsQuery =new SmsQuery();
				smsQuery.setId(rs.getString("id"));
				smsQuery.setSendDate(rs.getString("send_date"));
				smsQuery.setSendHour(rs.getInt("send_hour"));
				smsQuery.setSendMinutes(rs.getInt("send_minutes"));
				smsQuery.setSmsType(rs.getString("sms_type"));
				smsQuery.setDep(rs.getString("dep"));
				smsQuery.setUserName(rs.getString("user_name"));
				smsQuery.setContent(rs.getString("content"));
				smsQuery.setStatus(rs.getString("status"));
				smsQuery.setMobile(rs.getString("mobile"));
				smsQuery.setReceiveName(rs.getString("receiver_name"));
				return smsQuery;
			}}, page);
	}
	
	/**
	 * 返回指定单位、指定时间段的部门的各种状态的短信统计值
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param unit_Id
	 *            单位unit_Id
	 * @return List,其内为 Id,Status,数量。
	 */
	public List getGroupCountByDep(String startDate, String endDate, String unitId) {
		StringBuffer hsql = new StringBuffer();
		// 按部门形成部门短信发送情况
		hsql.append(" SELECT dep_id,sum(sucesscount) as sucesscount,sum(failcount) as failcount ");
		hsql.append(" FROM ");
		hsql.append(" (");
		// 成功发送的短信条数
		hsql.append(" SELECT dep_id,sum(NVL(item_count,0)) as sucesscount,0 as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status = '1' AND a.unit_Id = b.unit_Id AND a.unit_Id = '");
		hsql.append(unitId);
		hsql.append("' and send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY dep_id");

		// 两种短信条数合并相加
		hsql.append(" UNION");

		// 失败发送的短信条数
		hsql.append(" SELECT dep_id,0,sum(NVL(item_count,0)) as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status <> '1' AND a.unit_Id = b.unit_Id AND a.unit_Id ='");
		hsql.append(unitId);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY dep_id");
		hsql.append(" ) ");
		hsql.append(" GROUP BY dep_id");
		
		return getJdbcTemplate().queryForList(hsql.toString());
	}

	/**
	 * 返回指定单位、指定时间段的各种短信类型的各种状态的短信统计值
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param unit_Id
	 *            单位unit_Id
	 * @return List,其内为 Id,Status,数量。
	 */
	public List getGroupCountBySmsType(String startDate, String endDate,
			String unitId) {
		StringBuffer hsql = new StringBuffer();
		// 按短信类型形成短信发送情况
		hsql.append(" SELECT sms_type,sum(sucesscount) as sucesscount,sum(failcount) as failcount ");
		hsql.append(" FROM ");
		hsql.append(" (");
		// 成功发送的短信条数
		hsql.append(" SELECT sms_type,sum(NVL(item_count,0)) as sucesscount,0 as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status = '1' AND a.unit_Id = b.unit_Id AND a.unit_Id ='");
		hsql.append(unitId);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY sms_type");

		// 两种短信条数合并相加
		hsql.append(" UNION");

		// 失败发送的短信条数
		hsql.append(" SELECT sms_type,0,sum(NVL(item_count,0)) as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status <> '1' AND a.unit_Id = b.unit_Id AND a.unit_Id ='");
		hsql.append(unitId);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY sms_type");
		hsql.append(" )");
		hsql.append(" GROUP BY sms_type");
		
		return getJdbcTemplate().queryForList(hsql.toString());
	}
	
	public List getPersonalSmsTypeCount(String startDate, String endDate, String userId) {
		StringBuffer hsql = new StringBuffer();
		// 按短信类型形成短信发送情况
		hsql.append(" SELECT sms_type,sum(sucesscount) as sucesscount,sum(failcount) as failcount ");
		hsql.append(" FROM ");
		hsql.append(" (");
		// 成功发送的短信条数
		hsql.append(" SELECT sms_type,sum(NVL(item_count,0)) as sucesscount,0 as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status = '1' AND a.unit_Id = b.unit_Id AND a.user_id ='");
		hsql.append(userId);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY sms_type");

		// 两种短信条数合并相加
		hsql.append(" UNION");

		// 失败发送的短信条数
		hsql.append(" SELECT sms_type,0,sum(NVL(item_count,0)) as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status <> '1' AND a.unit_Id = b.unit_Id AND a.user_id ='");
		hsql.append(userId);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY sms_type");
		hsql.append(" ) ");
		hsql.append(" GROUP BY sms_type");
		
		return getJdbcTemplate().queryForList(hsql.toString());
	}

	/**
	 * 返回指定单位、指定时间段的发送用户的各种状态的短信统计值
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param unit_Id
	 *            单位unit_Id
	 * @return List,其内为 Id,Status,数量。
	 */
	public List getGroupCountByUser(String startDate, String endDate, String unitId) {
		StringBuffer hsql = new StringBuffer();
		// 按短信类型形成短信发送情况
		hsql.append(" SELECT user_id,sum(sucesscount) as sucesscount,sum(failcount) as failcount ");
		hsql.append(" FROM ");
		hsql.append(" (");
		// 成功发送的短信条数
		hsql.append(" SELECT a.user_id,sum(NVL(item_count,0)) as sucesscount,0 as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status = '1' AND a.unit_Id = b.unit_Id AND a.unit_Id ='");
		hsql.append(unitId);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY a.user_id");

		// 两种短信条数合并相加
		hsql.append(" UNION");

		// 失败发送的短信条数
		hsql.append(" SELECT a.user_id,0,sum(NVL(item_count,0)) as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status <> '1' AND a.unit_Id = b.unit_Id AND a.unit_Id ='");
		hsql.append(unitId);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY a.user_id");
		hsql.append(" ) ");
		hsql.append(" GROUP BY user_id");
		
		return getJdbcTemplate().queryForList(hsql.toString());
	}

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
			int topItems) {
		StringBuffer hsql = new StringBuffer();
		// 按短信类型形成短信发送情况
		hsql.append(" SELECT  sms_type,content,send_date,send_hour,send_minutes,sum(sucesscount) as sucesscount,sum(failcount) as failcount ");
		hsql.append(" FROM ");
		hsql.append(" (");
		// 成功发送的短信条数
		hsql.append(" SELECT sms_type,content,send_date,send_hour,send_minutes,sum(NVL(item_count,0)) as sucesscount,0 as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id  AND b.status = '1' AND a.unit_Id = b.unit_Id AND a.user_id ='");
		hsql.append(userid);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY sms_type,content,send_date,send_hour,send_minutes");

		// 两种短信条数合并相加
		hsql.append(" UNION");

		// 失败发送的短信条数
		hsql.append(" SELECT  sms_type,content,send_date,send_hour,send_minutes,0,sum(NVL(item_count,0)) as failcount");
		hsql.append(" FROM ");
		hsql.append(" base_sms_batch a,base_sms_send b");
		hsql.append(" WHERE b.batch_id = a.id o AND b.status <> '1' AND a.unit_Id = b.unit_Id AND a.user_id ='");
		hsql.append(userid);
		hsql.append("' AND send_date>='");
		hsql.append(startDate);
		hsql.append("' AND send_date <= '");
		hsql.append(endDate);
		hsql.append("' GROUP BY sms_type,content,send_date,send_hour,send_minutes");
		hsql.append(" ) ");
		hsql.append(" GROUP BY sms_type,content,send_date,send_hour,send_minutes");
		
		return getJdbcTemplate().queryForList(hsql.toString());
	}

}
