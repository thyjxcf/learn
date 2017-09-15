package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.dailyoffice.constant.OfficeBusinessTripConstants;
import net.zdsoft.office.dailyoffice.dao.OfficeBusinessTripDao;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.survey.constant.OfficeSurveyConstants;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;
/**
 * office_business_trip
 * @author 
 * 
 */
public class OfficeBusinessTripDaoImpl extends BaseDao<OfficeBusinessTrip> implements OfficeBusinessTripDao{

	@Override
	public OfficeBusinessTrip setField(ResultSet rs) throws SQLException{
		OfficeBusinessTrip officeBusinessTrip = new OfficeBusinessTrip();
		officeBusinessTrip.setId(rs.getString("id"));
		officeBusinessTrip.setPlace(rs.getString("place"));
		officeBusinessTrip.setBeginTime(rs.getTimestamp("begin_time"));
		officeBusinessTrip.setEndTime(rs.getTimestamp("end_time"));
		officeBusinessTrip.setDays(rs.getDouble("days"));
		officeBusinessTrip.setTripReason(rs.getString("trip_reason"));
		officeBusinessTrip.setState(rs.getString("state"));
		officeBusinessTrip.setFlowId(rs.getString("flow_id"));
		officeBusinessTrip.setUnitId(rs.getString("unit_id"));
		officeBusinessTrip.setApplyUserId(rs.getString("apply_user_id"));
		officeBusinessTrip.setCreateTime(rs.getTimestamp("create_time"));
		officeBusinessTrip.setIsDeleted(rs.getBoolean("is_deleted"));
		officeBusinessTrip.setInvalidUser(rs.getString("invalid_user"));
		return officeBusinessTrip;
	}

	@Override
	public OfficeBusinessTrip save(OfficeBusinessTrip officeBusinessTrip){
		String sql = "insert into office_business_trip(id, place, begin_time, end_time, days, trip_reason, state, flow_id, unit_id, apply_user_id, create_time, is_deleted,invalid_user) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeBusinessTrip.getId())){
			officeBusinessTrip.setId(createId());
		}
		Object[] args = new Object[]{
			officeBusinessTrip.getId(), officeBusinessTrip.getPlace(), 
			officeBusinessTrip.getBeginTime(), officeBusinessTrip.getEndTime(), 
			officeBusinessTrip.getDays(), officeBusinessTrip.getTripReason(), 
			officeBusinessTrip.getState(), officeBusinessTrip.getFlowId(), 
			officeBusinessTrip.getUnitId(), officeBusinessTrip.getApplyUserId(), 
			officeBusinessTrip.getCreateTime(), officeBusinessTrip.getIsDeleted(),
			officeBusinessTrip.getInvalidUser()
		};
		update(sql, args);
		return officeBusinessTrip;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_business_trip where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeBusinessTrip officeBusinessTrip){
		String sql = "update office_business_trip set place = ?, begin_time = ?, end_time = ?, days = ?, trip_reason = ?, state = ?, flow_id = ?, unit_id = ?, apply_user_id = ?, create_time = ?, is_deleted = ?,invalid_user=? where id = ?";
		Object[] args = new Object[]{
			officeBusinessTrip.getPlace(), officeBusinessTrip.getBeginTime(), 
			officeBusinessTrip.getEndTime(), officeBusinessTrip.getDays(), 
			officeBusinessTrip.getTripReason(), officeBusinessTrip.getState(), 
			officeBusinessTrip.getFlowId(), officeBusinessTrip.getUnitId(), 
			officeBusinessTrip.getApplyUserId(), officeBusinessTrip.getCreateTime(), 
			officeBusinessTrip.getIsDeleted(), officeBusinessTrip.getInvalidUser(),officeBusinessTrip.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBusinessTrip getOfficeBusinessTripById(String id){
		String sql = "select * from office_business_trip where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBusinessTrip> getOfficeBusinessTripMapByIds(String[] ids){
		String sql = "select * from office_business_trip where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripList(){
		String sql = "select * from office_business_trip";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripPage(Pagination page){
		String sql = "select * from office_business_trip";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdList(String unitId){
		String sql = "select * from office_business_trip where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_business_trip where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdUserIdPage(
			String unitId, String userId, String states, Pagination page) {
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_business_trip where unit_id = ? ");
		argsList.add(unitId);
		if(StringUtils.isNotBlank(states)&&Integer.parseInt(states)!=Constants.LEAVE_APPLY_ALL){
				sql.append(" and state = ?");
				argsList.add(Integer.parseInt(states));
		}else{
			sql.append(" and state <>8");
		}
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and apply_user_id = ?");
			argsList.add(userId);
		}
		sql.append(" order by state,create_time desc");
		if(page != null)
			return query(sql.toString(), argsList.toArray(), new MultiRow(), page);
		else
			return query(sql.toString(), argsList.toArray(), new MultiRow());
	}

	@Override
	public boolean isExistConflict(String id, String applyUserId,
			Date beginTime, Date endTime) {
		String sql = "select count(1) from office_business_trip where state !=4 and apply_user_id = ? and ((begin_time <= ? and end_time >= ?) or (begin_time <= ? and end_time >= ?) or (begin_time >= ? and end_time <= ?))";
		
		String sql1 = "select count(1) from office_business_trip where state !=4 and apply_user_id = ? and id != ? and ((begin_time <= ? and end_time >= ?) or (begin_time <= ? and end_time >= ?) or (begin_time >= ? and end_time <= ?))";
		int i = 0;
		if(StringUtils.isNotBlank(id)){
			i = queryForInt(sql1, new Object[] {applyUserId, id, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}else{
			i = queryForInt(sql, new Object[] {applyUserId, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}
		if(i > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByIds(String[] ids) {
		String sql = "select * from office_business_trip where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public Map<String, OfficeBusinessTrip> getOfficeBusinessTripMapByFlowIds(
			String[] array) {
		String sql = "select * from office_business_trip where flow_id in";
		return queryForInSQL(sql, null, array, new MapRowMapper<String, OfficeBusinessTrip>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeBusinessTrip mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
	
	//获取日期范围内的外出记录
	public List<OfficeBusinessTrip> getListByStarttimeAndEndtime(Date startTime,Date endTime,String[] userIds){
		StringBuffer sql = new StringBuffer("select * from office_business_trip where state = '3' and is_deleted=0 ");
		List<Object>list=new ArrayList<Object>();
		if(startTime!=null){
			sql.append(" and end_time >=?");
			list.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and begin_time<=?");
			list.add(endTime);
		}
		sql.append(" and apply_user_id in");
		return queryForInSQL(sql.toString(), list.toArray(), userIds, new MultiRow());
	}

	@Override
	public List<OfficeBusinessTrip> HaveDoAudit(String userId,boolean invalid, Pagination page) {
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String findSql = "select distinct leave.* from office_business_trip leave,jbpm_hi_task task where  leave.flow_id = task.proc_inst_id";
		StringBuffer sb = new StringBuffer();
		sb.append(findSql);
		if(invalid){
			sb.append(" and (leave.state=3 or leave.state=4)");
		}else{
			sb.append(" and leave.state=8");
		}
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" order by  state,create_time desc");
		return query(sb.toString(),obj.toArray(), new MultiRow(),page);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripsByUnitIdAndOthers(
			String unitId, String state, Date startTime, Date endTime,
			String applyUserName,Pagination page) {
		List<Object> obj=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_business_trip where unit_id = ? ");
		obj.add(unitId);
		if(org.apache.commons.lang3.StringUtils.isNotBlank(state)){
			sb.append(" and state=?");
			obj.add(state);
		}else{
			sb.append(" and state<>8 and state<>1");
		}
		if(startTime!=null){
			sb.append(" and begin_time>=?");
			obj.add(startTime);
		}
		if(endTime!=null){
			sb.append(" and end_time<=?");
			obj.add(endTime);
		}
		if(org.apache.commons.lang3.StringUtils.isNotBlank(applyUserName)){
			sb.append(" and exists(select 1 from base_user where base_user.id=office_business_trip.apply_user_id and base_user.real_name like '%"+applyUserName+"%' )");
		}
		if(page!=null){
			return query(sb.toString(), obj.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), obj.toArray(), new MultiRow());
		}
	}

	@Override
	public List<OfficeBusinessTrip> getListByUnitIdAndDate(String unitId,
			Date date) {
		List<Object>list=new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_business_trip where state =3 and is_deleted=0 and unit_id=?");
		list.add(unitId);
		if(date!=null){
			sql.append(" and to_char(begin_time,'yyyy-MM-dd') <= ? and to_char(end_time,'yyyy-MM-dd')>=?");
			list.add(DateUtils.date2String(date, "yyyy-MM-dd"));list.add(DateUtils.date2String(date, "yyyy-MM-dd"));
		}
		return query(sql.toString(), list.toArray(),new MultiRow());
	}
	
}

	