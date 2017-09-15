package net.zdsoft.office.studentBackSchool.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentBackSchool.dao.InspectionHolidaysInfoDao;
import net.zdsoft.office.studentBackSchool.entity.InspectionHolidaysInfo;

public class InspectionHolidaysInfoDaoImpl extends BaseDao<InspectionHolidaysInfo> implements InspectionHolidaysInfoDao{

	
	@Override
	public InspectionHolidaysInfo setField(
			ResultSet rs) throws SQLException {
		InspectionHolidaysInfo holiday=new InspectionHolidaysInfo();
		holiday.setEndDate(rs.getDate("endDate"));
		holiday.setStartDate(rs.getDate("startDate"));
		holiday.setName(rs.getString("name"));
		holiday.setUnitId(rs.getString("unit_id"));
		holiday.setId(rs.getString("id"));
		holiday.setCreationTime(rs.getTimestamp("creation_time"));
		holiday.setModifyTime(rs.getTimestamp("modify_time"));
		return holiday;
	}
	@Override
	public void insertInspectionHolidaysInfo(InspectionHolidaysInfo holidayInfo) {
		String sql = "insert into inspection_holidays_info(id, unit_id, name, startDate, endDate, creation_time, modify_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(holidayInfo.getId())){
			holidayInfo.setId(createId());
		}
		Object[] args = new Object[]{
			holidayInfo.getId(), holidayInfo.getUnitId(), 
			holidayInfo.getName(),holidayInfo.getStartDate(),
			holidayInfo.getEndDate(),
			holidayInfo.getCreationTime(), 
			holidayInfo.getModifyTime()
		};
		update(sql, args);
	}

	@Override
	public void updateInspectionHolidaysInfo(InspectionHolidaysInfo holidayInfo) {
		String sql = "update inspection_holidays_info set unit_id = ?, startDate=?,name=?,endDate=?, creation_time = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			holidayInfo.getUnitId(), holidayInfo.getStartDate(),
			holidayInfo.getName(),holidayInfo.getEndDate(),
			holidayInfo.getCreationTime(), holidayInfo.getModifyTime(), 
			holidayInfo.getId()
		};
		update(sql, args);
		
	}

	@Override
	public List<InspectionHolidaysInfo> findInspectionHolidaysInfobyUnitId(
			String unitId,Pagination page) {
		String sql = "select * from inspection_holidays_info  where unit_id = ? order by creation_time desc";
		if(page==null){
			return query(sql, new Object[]{unitId}, new MultiRow());
		}
		return query(sql, new Object[]{unitId}, new MultiRow(),page);
	}

	@Override
	public InspectionHolidaysInfo findInspectionHolidaysInfobyId(String id) {
		String sql = "select * from inspection_holidays_info  where id = ?";
		return query(sql, new Object[]{id}, new SingleRow());
	}
	@Override
	public void deleteInspectionHolidaysInfobyId(String id) {
		String sql = "delete from inspection_holidays_info where id = ?";
		update(sql, new Object[]{id});
	}

	

}
