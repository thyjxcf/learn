package net.zdsoft.office.dailyoffice.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeTimeSetDao;
import net.zdsoft.office.dailyoffice.entity.OfficeTimeSet;

import org.apache.commons.lang.StringUtils;
/**
 * office_time_set
 * @author 
 * 
 */
public class OfficeTimeSetDaoImpl extends BaseDao<OfficeTimeSet> implements OfficeTimeSetDao{

	@Override
	public OfficeTimeSet setField(ResultSet rs) throws SQLException{
		OfficeTimeSet officeTimeSet = new OfficeTimeSet();
		officeTimeSet.setId(rs.getString("id"));
		officeTimeSet.setUnitId(rs.getString("unit_id"));
		officeTimeSet.setStartTime(rs.getString("start_time"));
		officeTimeSet.setEndTime(rs.getString("end_time"));
		officeTimeSet.setTimeInterval(rs.getInt("time_interval"));
		officeTimeSet.setNoonStartTime(rs.getString("noon_start_time"));
		officeTimeSet.setNoonEndTime(rs.getString("noon_end_time"));
		return officeTimeSet;
	}

	@Override
	public OfficeTimeSet save(OfficeTimeSet officeTimeSet){
		String sql = "insert into office_time_set(id, unit_id, start_time, end_time, time_interval,noon_start_time,noon_end_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeTimeSet.getId())){
			officeTimeSet.setId(createId());
		}
		Object[] args = new Object[]{
			officeTimeSet.getId(), officeTimeSet.getUnitId(), 
			officeTimeSet.getStartTime(), officeTimeSet.getEndTime(), 
			officeTimeSet.getTimeInterval(), officeTimeSet.getNoonStartTime(),
			officeTimeSet.getNoonEndTime()
		};
		update(sql, args);
		return officeTimeSet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_time_set where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeTimeSet officeTimeSet){
		String sql = "update office_time_set set unit_id = ?, start_time = ?, end_time = ?, time_interval = ?, noon_start_time = ?, noon_end_time = ? where id = ?";
		Object[] args = new Object[]{
			officeTimeSet.getUnitId(), officeTimeSet.getStartTime(), 
			officeTimeSet.getEndTime(), officeTimeSet.getTimeInterval(), 
			officeTimeSet.getNoonStartTime(), officeTimeSet.getNoonEndTime(),
			officeTimeSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeTimeSet getOfficeTimeSetById(String id){
		String sql = "select * from office_time_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTimeSet> getOfficeTimeSetMapByIds(String[] ids){
		String sql = "select * from office_time_set where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTimeSet> getOfficeTimeSetList(){
		String sql = "select * from office_time_set";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeTimeSet> getOfficeTimeSetPage(Pagination page){
		String sql = "select * from office_time_set";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public OfficeTimeSet getOfficeTimeSetByUnitId(String unitId){
		String sql = "select * from office_time_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new SingleRow());
	}

	@Override
	public List<OfficeTimeSet> getOfficeTimeSetByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_time_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}