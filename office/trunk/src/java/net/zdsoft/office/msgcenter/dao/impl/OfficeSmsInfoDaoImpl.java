package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.msgcenter.entity.OfficeSmsInfo;
import net.zdsoft.office.msgcenter.dao.OfficeSmsInfoDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
/**
 * office_sms_info
 * @author 
 * 
 */
public class OfficeSmsInfoDaoImpl extends BaseDao<OfficeSmsInfo> implements OfficeSmsInfoDao{

	@Override
	public OfficeSmsInfo setField(ResultSet rs) throws SQLException{
		OfficeSmsInfo officeSmsInfo = new OfficeSmsInfo();
		officeSmsInfo.setId(rs.getString("id"));
		officeSmsInfo.setUnitId(rs.getString("unit_id"));
		officeSmsInfo.setBatchId(rs.getString("batch_id"));
		officeSmsInfo.setSendUserId(rs.getString("send_user_id"));
		officeSmsInfo.setMsg(rs.getString("msg"));
		officeSmsInfo.setSendTime(rs.getString("send_time"));
		officeSmsInfo.setFeedbackDescription(rs.getString("feedback_description"));
		officeSmsInfo.setCreateTime(rs.getTimestamp("create_time"));
		return officeSmsInfo;
	}

	@Override
	public OfficeSmsInfo save(OfficeSmsInfo officeSmsInfo){
		String sql = "insert into office_sms_info(id, unit_id, batch_id, send_user_id, msg, send_time, feedback_description, create_time) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSmsInfo.getId())){
			officeSmsInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeSmsInfo.getId(), officeSmsInfo.getUnitId(), 
			officeSmsInfo.getBatchId(), officeSmsInfo.getSendUserId(), 
			officeSmsInfo.getMsg(), officeSmsInfo.getSendTime(), 
			officeSmsInfo.getFeedbackDescription(), officeSmsInfo.getCreateTime()
		};
		update(sql, args);
		return officeSmsInfo;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_sms_info where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSmsInfo officeSmsInfo){
		String sql = "update office_sms_info set unit_id = ?, batch_id = ?, send_user_id = ?, msg = ?, send_time = ?, feedback_description = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeSmsInfo.getUnitId(), officeSmsInfo.getBatchId(), 
			officeSmsInfo.getSendUserId(), officeSmsInfo.getMsg(), 
			officeSmsInfo.getSendTime(), officeSmsInfo.getFeedbackDescription(), 
			officeSmsInfo.getCreateTime(), officeSmsInfo.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSmsInfo getOfficeSmsInfoById(String id){
		String sql = "select * from office_sms_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSmsInfo> getOfficeSmsInfoMapByIds(String[] ids){
		String sql = "select * from office_sms_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoList(){
		String sql = "select * from office_sms_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoPage(Pagination page){
		String sql = "select * from office_sms_info";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoByUnitIdList(String unitId){
		String sql = "select * from office_sms_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_sms_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoByConditions(String unitId, String userId, 
			String searchBeginTime, String searchEndTime, Pagination page){
		String sql = "select * from office_sms_info where unit_id = ? ";
		StringBuffer sbf = new StringBuffer(sql);
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(StringUtils.isNotBlank(userId)){
			sbf.append(" and send_user_id = ? ");
			objs.add(userId);
		}
		if(StringUtils.isNotBlank(searchBeginTime)){
			sbf.append(" and length(send_time)=14 and to_date(substr(send_time,1,8),'yyyy-MM-dd') >= to_date(?,'yyyy-MM-dd') ");
			objs.add(searchBeginTime);
		}
		if(StringUtils.isNotBlank(searchEndTime)){
			sbf.append(" and length(send_time)=14 and to_date(substr(send_time,1,8),'yyyy-MM-dd') <= to_date(?,'yyyy-MM-dd') ");
			objs.add(searchEndTime);
		}
		sbf.append(" order by send_time desc");
		if(page != null){
			return query(sbf.toString(), objs.toArray(), new MultiRow(), page);
		}else{
			return query(sbf.toString(), objs.toArray(), new MultiRow());
		}
	}
}
