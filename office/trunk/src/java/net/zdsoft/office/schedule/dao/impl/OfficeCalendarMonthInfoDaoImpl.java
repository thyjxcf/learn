package net.zdsoft.office.schedule.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dao.OfficeCalendarMonthInfoDao;
import net.zdsoft.office.schedule.entity.OfficeCalendarMonthInfo;

import org.apache.commons.lang3.StringUtils;
/**
 * office_calendar_month_info
 * @author 
 * 
 */
public class OfficeCalendarMonthInfoDaoImpl extends BaseDao<OfficeCalendarMonthInfo> implements OfficeCalendarMonthInfoDao{

	@Override
	public OfficeCalendarMonthInfo setField(ResultSet rs) throws SQLException{
		OfficeCalendarMonthInfo officeCalendarMonthInfo = new OfficeCalendarMonthInfo();
		officeCalendarMonthInfo.setId(rs.getString("id"));
		officeCalendarMonthInfo.setMonthDate(rs.getTimestamp("month_date"));
		officeCalendarMonthInfo.setContent(rs.getString("content"));
		officeCalendarMonthInfo.setUnitId(rs.getString("unit_id"));
		return officeCalendarMonthInfo;
	}

	@Override
	public OfficeCalendarMonthInfo save(OfficeCalendarMonthInfo officeCalendarMonthInfo){
		String sql = "insert into office_calendar_month_info(id, month_date, content, unit_id) values(?,?,?,?)";
		if (StringUtils.isBlank(officeCalendarMonthInfo.getId())){
			officeCalendarMonthInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeCalendarMonthInfo.getId(), officeCalendarMonthInfo.getMonthDate(), 
			officeCalendarMonthInfo.getContent(), officeCalendarMonthInfo.getUnitId()
		};
		update(sql, args);
		return officeCalendarMonthInfo;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_calendar_month_info where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCalendarMonthInfo officeCalendarMonthInfo){
		String sql = "update office_calendar_month_info set month_date = ?, content = ?, unit_id = ? where id = ?";
		Object[] args = new Object[]{
			officeCalendarMonthInfo.getMonthDate(), officeCalendarMonthInfo.getContent(), 
			officeCalendarMonthInfo.getUnitId(), officeCalendarMonthInfo.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeCalendarMonthInfo getOfficeCalendarMonthInfoById(String id){
		String sql = "select * from office_calendar_month_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoMapByIds(String[] ids){
		String sql = "select * from office_calendar_month_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoList(){
		String sql = "select * from office_calendar_month_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoPage(Pagination page){
		String sql = "select * from office_calendar_month_info";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdList(String unitId){
		String sql = "select * from office_calendar_month_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_calendar_month_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public Map<String, OfficeCalendarMonthInfo> getCalendarMonthInfoMapByUnitId(
			String unitId) {

		String sql = "select * from office_calendar_month_info where unit_id = ? ";
		return queryForMap(sql, new Object[]{unitId}, new MapRow(){
			
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return DateUtils.date2String(rs.getDate("month_date"),"yyyy-MM");
			}
			
			public OfficeCalendarMonthInfo mapRowValue(ResultSet rs, int numRow)
					throws SQLException {
				OfficeCalendarMonthInfo officeCalendarMonthInfo = new OfficeCalendarMonthInfo();
				officeCalendarMonthInfo.setId(rs.getString("id"));
				officeCalendarMonthInfo.setMonthDate(rs.getTimestamp("month_date"));
				officeCalendarMonthInfo.setContent(rs.getString("content"));
				officeCalendarMonthInfo.setUnitId(rs.getString("unit_id"));
				return officeCalendarMonthInfo;
			}
		});
	
	}
}

