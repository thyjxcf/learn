package net.zdsoft.office.schedule.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dao.OfficeCalendarSemesterDao;
import net.zdsoft.office.schedule.entity.OfficeCalendarSemester;

import org.apache.commons.lang3.StringUtils;
/**
 * office_calendar_semester
 * @author 
 * 
 */
public class OfficeCalendarSemesterDaoImpl extends BaseDao<OfficeCalendarSemester> implements OfficeCalendarSemesterDao{

	@Override
	public OfficeCalendarSemester setField(ResultSet rs) throws SQLException{
		OfficeCalendarSemester officeCalendarSemester = new OfficeCalendarSemester();
		officeCalendarSemester.setId(rs.getString("id"));
		officeCalendarSemester.setAcadyear(rs.getString("acadyear"));
		officeCalendarSemester.setSemester(rs.getInt("semester"));
		officeCalendarSemester.setBeginDate(rs.getTimestamp("begin_date"));
		officeCalendarSemester.setEndDate(rs.getTimestamp("end_date"));
		officeCalendarSemester.setContent(rs.getString("content"));
		officeCalendarSemester.setUnitId(rs.getString("unit_id"));
		officeCalendarSemester.setCalyear(rs.getInt("calyear"));
		return officeCalendarSemester;
	}

	@Override
	public OfficeCalendarSemester save(OfficeCalendarSemester officeCalendarSemester){
		String sql = "insert into office_calendar_semester(id, acadyear, semester, begin_date, end_date, content, unit_id, calyear) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeCalendarSemester.getId())){
			officeCalendarSemester.setId(createId());
		}
		Object[] args = new Object[]{
			officeCalendarSemester.getId(), officeCalendarSemester.getAcadyear(), 
			officeCalendarSemester.getSemester(), officeCalendarSemester.getBeginDate(), 
			officeCalendarSemester.getEndDate(), officeCalendarSemester.getContent(), 
			officeCalendarSemester.getUnitId(), officeCalendarSemester.getCalyear()
		};
		update(sql, args);
		return officeCalendarSemester;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_calendar_semester where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCalendarSemester officeCalendarSemester){
		String sql = "update office_calendar_semester set acadyear = ?, semester = ?, begin_date = ?, end_date = ?, unit_id = ?, calyear = ? where id = ?";
		Object[] args = new Object[]{
			officeCalendarSemester.getAcadyear(), officeCalendarSemester.getSemester(), 
			officeCalendarSemester.getBeginDate(), officeCalendarSemester.getEndDate(), 
			officeCalendarSemester.getUnitId(), 
			officeCalendarSemester.getCalyear(), officeCalendarSemester.getId()
		};
		return update(sql, args);
	}
	@Override
	public void updateContent(OfficeCalendarSemester officeCalendarSemester) {
		String sql = "update office_calendar_semester set content = ? where id = ?";
		Object[] args = new Object[]{
			officeCalendarSemester.getContent(), officeCalendarSemester.getId()
		};
		update(sql, args);
	}
	@Override
	public OfficeCalendarSemester getOfficeCalendarSemesterById(String id){
		String sql = "select * from office_calendar_semester where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCalendarSemester> getOfficeCalendarSemesterMapByIds(String[] ids){
		String sql = "select * from office_calendar_semester where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterList(){
		String sql = "select * from office_calendar_semester";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterPage(Pagination page){
		String sql = "select * from office_calendar_semester";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterByUnitIdList(String unitId){
		String sql = "select * from office_calendar_semester where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_calendar_semester where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public OfficeCalendarSemester getCalendarSemester(String calyear,
			String unitId) {
		String sql = "select * from office_calendar_semester where  unit_id = ? and calyear = ?";
		return (OfficeCalendarSemester) query(sql, new Object[]{unitId, Integer.parseInt(calyear) }, new SingleRow());
	}
	@Override
	public OfficeCalendarSemester getCalendarSemester(String acadyear,
			String semester, String unitId) {
		String sql = "select * from office_calendar_semester where  unit_id = ? and acadyear = ? and semester = ?";
		return (OfficeCalendarSemester) query(sql, new Object[]{unitId, acadyear, semester }, new SingleRow());
	}
}
