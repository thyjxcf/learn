package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarApplyDao;
import net.zdsoft.office.dailyoffice.entity.OfficeCarApply;

import org.apache.commons.lang.StringUtils;
/**
 * office_car_apply
 * @author 
 * 
 */
public class OfficeCarApplyDaoImpl extends BaseDao<OfficeCarApply> implements OfficeCarApplyDao{

	@Override
	public OfficeCarApply setField(ResultSet rs) throws SQLException{
		OfficeCarApply officeCarApply = new OfficeCarApply();
		officeCarApply.setId(rs.getString("id"));
		officeCarApply.setUnitId(rs.getString("unit_id"));
		officeCarApply.setApplyUserId(rs.getString("apply_user_id"));
		officeCarApply.setApplyTime(rs.getTimestamp("apply_time"));
		officeCarApply.setDeptId(rs.getString("dept_id"));
		officeCarApply.setLinkUserId(rs.getString("link_user_id"));
		officeCarApply.setMobilePhone(rs.getString("mobile_phone"));
		officeCarApply.setPersonNumber(rs.getInt("person_number"));
		officeCarApply.setReason(rs.getString("reason"));
		officeCarApply.setCarLocation(rs.getString("car_location"));
		officeCarApply.setIsGoback(rs.getBoolean("is_goback"));
		officeCarApply.setIsNeedWaiting(rs.getBoolean("is_need_waiting"));
		officeCarApply.setWaitingTime(rs.getTimestamp("waiting_time"));
		officeCarApply.setUseTime(rs.getTimestamp("use_time"));
		officeCarApply.setIsDeleted(rs.getBoolean("is_deleted"));
		officeCarApply.setAuditUserId(rs.getString("audit_user_id"));
		officeCarApply.setState(rs.getInt("state"));
		officeCarApply.setAuditTime(rs.getTimestamp("audit_time"));
		officeCarApply.setArea(rs.getString("area"));
		officeCarApply.setCarId(rs.getString("car_id"));
		officeCarApply.setDriverId(rs.getString("driver_id"));
		officeCarApply.setRemark(rs.getString("remark"));
		officeCarApply.setOvertimeNumber(rs.getFloat("overtime_number"));
		officeCarApply.setIsOvertime(rs.getBoolean("is_overtime"));
		return officeCarApply;
	}

	@Override
	public OfficeCarApply save(OfficeCarApply officeCarApply){
		String sql = "insert into office_car_apply(id, unit_id, apply_user_id, apply_time, dept_id, link_user_id, mobile_phone, person_number, reason, car_location, is_goback, is_need_waiting, waiting_time, use_time, is_deleted, audit_user_id, state, audit_time, area, car_id, driver_id, remark, overtime_number, is_overtime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeCarApply.getId())){
			officeCarApply.setId(createId());
		}
		Object[] args = new Object[]{
			officeCarApply.getId(), officeCarApply.getUnitId(), 
			officeCarApply.getApplyUserId(), officeCarApply.getApplyTime(), 
			officeCarApply.getDeptId(), officeCarApply.getLinkUserId(), 
			officeCarApply.getMobilePhone(), officeCarApply.getPersonNumber(), 
			officeCarApply.getReason(), officeCarApply.getCarLocation(), 
			officeCarApply.getIsGoback(), officeCarApply.getIsNeedWaiting(), 
			officeCarApply.getWaitingTime(), officeCarApply.getUseTime(), 
			officeCarApply.getIsDeleted(), officeCarApply.getAuditUserId(), 
			officeCarApply.getState(), officeCarApply.getAuditTime(), 
			officeCarApply.getArea(), officeCarApply.getCarId(), 
			officeCarApply.getDriverId(), officeCarApply.getRemark(), 
			officeCarApply.getOvertimeNumber(),
			officeCarApply.getIsOvertime(),
		};
		update(sql, args);
		return officeCarApply;
	}
	
	@Override
	public void updateState(OfficeCarApply officeCarApply) {
		String sql = "update office_car_apply set audit_user_id = ?, state = ?, audit_time = ?, area = ?, car_id = ?, driver_id = ?, remark = ?,is_overtime = ?,overtime_number = ? where id = ?";
		Object[] args = new Object[]{
			officeCarApply.getAuditUserId(), officeCarApply.getState(), 
			officeCarApply.getAuditTime(), officeCarApply.getArea(), 
			officeCarApply.getCarId(), officeCarApply.getDriverId(), 
			officeCarApply.getRemark(), 
			officeCarApply.getIsOvertime(),
			officeCarApply.getOvertimeNumber(),officeCarApply.getId()
		};
		update(sql, args);
	}
	
	@Override
	public void updateApplyState(OfficeCarApply officeCarApply){
		String sql = "update office_car_apply set audit_user_id = ?, state = ?, audit_time = ?, remark = ? where id = ?";
		Object[] args = new Object[]{
			officeCarApply.getAuditUserId(), officeCarApply.getState(), 
			officeCarApply.getAuditTime(), 
			officeCarApply.getRemark(), officeCarApply.getId()
		};
		update(sql, args);
	}
	
	@Override
	public void updateOverTimeNumber(OfficeCarApply officeCarApply) {
		String sql = "update office_car_apply set overtime_number = ?, is_overtime = ? where id = ?";
		Object[] args = new Object[]{
			officeCarApply.getOvertimeNumber(), 
			officeCarApply.getIsOvertime(),
			officeCarApply.getId()
		};
		update(sql, args);
	}
	
	@Override
	public boolean isExistUnUsed(String carId, String driverId) {
		String sql = "select count(1) from office_car_apply where (use_time > sysdate or waiting_time > sysdate) ";
		if(StringUtils.isNotBlank(carId)){
			sql += " and car_id like ? ";
			int i = queryForInt(sql, new Object[]{"%"+carId+"%"});
			if(i > 0){
				return true;
			}
		}else if (StringUtils.isNotBlank(driverId)){
			sql += " and driver_id like ? ";
			int i = queryForInt(sql, new Object[]{"%"+driverId+"%"});
			if(i > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer delete(String id){
		String sql = "update office_car_apply set is_deleted = 1 where id = ? ";
		return update(sql, id);
	}

	@Override
	public Integer update(OfficeCarApply officeCarApply){
		String sql = "update office_car_apply set link_user_id = ?, mobile_phone = ?, person_number = ?, reason = ?, car_location = ?, is_goback = ?, is_need_waiting = ?, waiting_time = ?, use_time = ?, state = ? where id = ?";
		Object[] args = new Object[]{
			officeCarApply.getLinkUserId(), officeCarApply.getMobilePhone(), 
			officeCarApply.getPersonNumber(), officeCarApply.getReason(), 
			officeCarApply.getCarLocation(), officeCarApply.getIsGoback(), 
			officeCarApply.getIsNeedWaiting(), officeCarApply.getWaitingTime(),
			officeCarApply.getUseTime(), officeCarApply.getState(), 
			officeCarApply.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeCarApply getOfficeCarApplyById(String id){
		String sql = "select * from office_car_apply where id = ? ";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	@Override
	public List<OfficeCarApply> getOfficeCarApplyListByArea(String unitId,
			String subId) {
		String sql = "select * from office_car_apply where unit_id = ? and area = ?";
		return query(sql, new Object[]{unitId,subId }, new MultiRow());
	}
	@Override
	public List<OfficeCarApply> getOfficeCarApplyByUnitIdPage(String unitId,
			String startTime, String endTime, String state, String driverId, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_car_apply where is_deleted = 0 and unit_id = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') <= str_to_date(date_format(use_time, '%Y-%m-%d'), '%Y-%m-%d')");
			objs.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(use_time, '%Y-%m-%d'), '%Y-%m-%d')");
			objs.add(endTime);
		}
		
		if(StringUtils.isNotBlank(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}else{
			sql.append(" and state <> 1 ");
		}
		if(StringUtils.isNotBlank(driverId)){
			sql.append(" and driver_id like (select '%'||id||'%' from office_car_driver where is_deleted = 0 and driver_id = ?) ");
			objs.add(driverId);
		}
		sql.append(" order by case when state=1 then 1 when state=2 then 2 when state=5 then 3 when state=3 then 4 when state=4 then 5 when state=6 then 6 end, use_time desc");
		if(page == null){
			return query(sql.toString(), objs.toArray(), new MultiRow());
		}
		return query(sql.toString(), objs.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeCarApply> getOfficeCarListPage(String unitId,
			String applyUserId, String startTime, String endTime,
			String state, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_car_apply where is_deleted = 0 and unit_id = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(StringUtils.isNotBlank(applyUserId)){
			sql.append(" and apply_user_id = ? ");
			objs.add(applyUserId);
		}
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') <= str_to_date(date_format(use_time, '%Y-%m-%d'), '%Y-%m-%d')");
			objs.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(use_time, '%Y-%m-%d'), '%Y-%m-%d')");
			objs.add(endTime);
		}
		if(StringUtils.isNotBlank(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}
		
		sql.append(" order by case when state=1 then 1 when state=2 then 2 when state=5 then 3 when state=3 then 4 when state=4 then 5 when state=6 then 6 end, use_time desc");
		return query(sql.toString(), objs.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeCarApply> getOfficeCarApplyByCarId(String carId) {
		String sql = "select * from office_car_apply where is_deleted = 0 and use_time > sysdate and car_id like ? order by use_time ";
		return query(sql, new Object[]{"%"+carId+"%"},new MultiRow());
	}

	@Override
	public List<OfficeCarApply> getOfficeCarApplyByUnitIdStatePage(
			String unitId, String startTime, String endTime, String state,String driverId) {
		StringBuffer sql = new StringBuffer("select * from office_car_apply where is_deleted = 0 and overtime_number>0 and overtime_number is not null  and unit_id = ?");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') <= str_to_date(date_format(use_time, '%Y-%m-%d'), '%Y-%m-%d')");
			objs.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(use_time, '%Y-%m-%d'), '%Y-%m-%d')");
			objs.add(endTime);
		}
		
		if(StringUtils.isNotBlank(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}
		if(StringUtils.isNotBlank(driverId)){
			sql.append(" and driver_id like '%"+ driverId +"%'");
		}
		return query(sql.toString(), objs.toArray(), new MultiRow());
	}
	
}