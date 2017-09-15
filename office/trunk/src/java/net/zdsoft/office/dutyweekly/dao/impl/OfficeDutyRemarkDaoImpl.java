package net.zdsoft.office.dutyweekly.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyweekly.entity.OfficeDutyRemark;
import net.zdsoft.office.dutyweekly.dao.OfficeDutyRemarkDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_remark
 * @author 
 * 
 */
public class OfficeDutyRemarkDaoImpl extends BaseDao<OfficeDutyRemark> implements OfficeDutyRemarkDao{

	@Override
	public OfficeDutyRemark setField(ResultSet rs) throws SQLException{
		OfficeDutyRemark officeDutyRemark = new OfficeDutyRemark();
		officeDutyRemark.setId(rs.getString("id"));
		officeDutyRemark.setUnitId(rs.getString("unit_id"));
		officeDutyRemark.setDutyWeeklyId(rs.getString("duty_weekly_id"));
		officeDutyRemark.setCreateUserId(rs.getString("create_user_id"));
		officeDutyRemark.setRemark(rs.getString("remark"));
		officeDutyRemark.setCreateTime(rs.getTimestamp("create_time"));
		return officeDutyRemark;
	}

	@Override
	public OfficeDutyRemark save(OfficeDutyRemark officeDutyRemark){
		String sql = "insert into office_duty_remark(id, unit_id, duty_weekly_id, create_user_id, remark, create_time) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDutyRemark.getId())){
			officeDutyRemark.setId(createId());
		}
		Object[] args = new Object[]{
			officeDutyRemark.getId(), officeDutyRemark.getUnitId(), 
			officeDutyRemark.getDutyWeeklyId(), officeDutyRemark.getCreateUserId(), 
			officeDutyRemark.getRemark(), officeDutyRemark.getCreateTime()
		};
		update(sql, args);
		return officeDutyRemark;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_duty_remark where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeDutyRemark officeDutyRemark){
		String sql = "update office_duty_remark set unit_id = ?, duty_weekly_id = ?, create_user_id = ?, remark = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeDutyRemark.getUnitId(), officeDutyRemark.getDutyWeeklyId(), 
			officeDutyRemark.getCreateUserId(), officeDutyRemark.getRemark(), 
			officeDutyRemark.getCreateTime(), officeDutyRemark.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDutyRemark getOfficeDutyRemarkById(String id){
		String sql = "select * from office_duty_remark where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyRemark> getOfficeDutyRemarkMapByIds(String[] ids){
		String sql = "select * from office_duty_remark where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkList(){
		String sql = "select * from office_duty_remark";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkPage(Pagination page){
		String sql = "select * from office_duty_remark";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkByUnitIdList(String unitId){
		String sql = "select * from office_duty_remark where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_duty_remark where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public void deleteOfficeDutyRemark(String unitId, String worklyId,
			Date dutyDate) {
		String sql="delete from office_duty_remark where unit_id=? and duty_weekly_id=? and to_date(to_char(create_time,'yyyy-MM-dd'),'yyyy-MM-dd')=?";
		update(sql, new Object[]{unitId,worklyId,dutyDate});
	}

	@Override
	public OfficeDutyRemark getOfficeDutyRemarkByUnitIdAndOthers(String unitId,
			String worklyId, Date dutyDate) {
		String sql="select * from office_duty_remark where unit_id=? and duty_weekly_id=? and to_date(to_char(create_time,'yyyy-MM-dd'),'yyyy-MM-dd')=?";
		return query(sql, new Object[]{unitId,worklyId,dutyDate}, new SingleRow());
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarksAndUnitIdAndMore(
			String unitId, String worklyId, Date[] dutyDate) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_duty_remark where unit_id=?");
		args.add(unitId);
		if(StringUtils.isNotBlank(worklyId)){
			sb.append(" and duty_weekly_id=?");
			args.add(worklyId);
		}
		if(ArrayUtils.isEmpty(dutyDate)){
			sb.append(" order by create_time");
			return query(sb.toString(), args.toArray(), new MultiRow());
		}else{
			sb.append(" and create_time in");
			return queryForInSQL(sb.toString(), args.toArray(), dutyDate, new MultiRow(),"order by create_time");
		}
	}
	
}
