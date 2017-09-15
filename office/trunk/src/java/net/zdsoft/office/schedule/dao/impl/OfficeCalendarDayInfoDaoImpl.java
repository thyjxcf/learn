package net.zdsoft.office.schedule.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dao.OfficeCalendarDayInfoDao;
import net.zdsoft.office.schedule.entity.OfficeCalendarDayInfo;

import org.apache.commons.lang3.StringUtils;
/**
 * office_calendar_day_info
 * @author 
 * 
 */
public class OfficeCalendarDayInfoDaoImpl extends BaseDao<OfficeCalendarDayInfo> implements OfficeCalendarDayInfoDao{

	@Override
	public OfficeCalendarDayInfo setField(ResultSet rs) throws SQLException{
		OfficeCalendarDayInfo officeCalendarDayInfo = new OfficeCalendarDayInfo();
		officeCalendarDayInfo.setId(rs.getString("id"));
		officeCalendarDayInfo.setRestDate(rs.getTimestamp("rest_date"));
		officeCalendarDayInfo.setContent(rs.getString("content"));
		officeCalendarDayInfo.setUnitId(rs.getString("unit_id"));
		return officeCalendarDayInfo;
	}

	@Override
	public OfficeCalendarDayInfo save(OfficeCalendarDayInfo officeCalendarDayInfo){
		String sql = "insert into office_calendar_day_info(id, rest_date, content, unit_id) values(?,?,?,?)";
		if (StringUtils.isBlank(officeCalendarDayInfo.getId())){
			officeCalendarDayInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeCalendarDayInfo.getId(), officeCalendarDayInfo.getRestDate(), 
			officeCalendarDayInfo.getContent(), officeCalendarDayInfo.getUnitId()
		};
		update(sql, args);
		return officeCalendarDayInfo;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_calendar_day_info where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCalendarDayInfo officeCalendarDayInfo){
		String sql = "update office_calendar_day_info set rest_date = ?, content = ?, unit_id = ? where id = ?";
		Object[] args = new Object[]{
			officeCalendarDayInfo.getRestDate(), officeCalendarDayInfo.getContent(), 
			officeCalendarDayInfo.getUnitId(), officeCalendarDayInfo.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeCalendarDayInfo getOfficeCalendarDayInfoById(String id){
		String sql = "select * from office_calendar_day_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCalendarDayInfo> getOfficeCalendarDayInfoMapByIds(String[] ids){
		String sql = "select * from office_calendar_day_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoList(){
		String sql = "select * from office_calendar_day_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoPage(Pagination page){
		String sql = "select * from office_calendar_day_info";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoByUnitIdList(String unitId){
		String sql = "select * from office_calendar_day_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_calendar_day_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public Map<String, OfficeCalendarDayInfo> getCalendarDayInfoMapByUnitId(
			Date beginDate, Date endDate, String unitId) {
		String sql = "select * from office_calendar_day_info where  unit_id = ? and rest_date >= ? and rest_date <= ? ";
		return queryForMap(sql, new Object[]{unitId, beginDate, endDate}, new MapRow(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return DateUtils.date2String(rs.getDate("rest_date"),"yyyy-MM-dd");
			}
			
			@Override
			public OfficeCalendarDayInfo mapRowValue(ResultSet rs, int numRow)
					throws SQLException {
				OfficeCalendarDayInfo officeCalendarDayInfo = new OfficeCalendarDayInfo();
				officeCalendarDayInfo.setId(rs.getString("id"));
				officeCalendarDayInfo.setRestDate(rs.getTimestamp("rest_date"));
				officeCalendarDayInfo.setContent(rs.getString("content"));
				officeCalendarDayInfo.setUnitId(rs.getString("unit_id"));
				return officeCalendarDayInfo;
			}
		});
	}
}

