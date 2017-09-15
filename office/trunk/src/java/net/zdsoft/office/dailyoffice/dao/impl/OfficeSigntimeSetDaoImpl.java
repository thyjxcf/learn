package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeSigntimeSetDao;
import net.zdsoft.office.dailyoffice.entity.OfficeSigntimeSet;

import org.apache.commons.lang.StringUtils;
/**
 * office_signtime_set
 * @author 
 * 
 */
public class OfficeSigntimeSetDaoImpl extends BaseDao<OfficeSigntimeSet> implements OfficeSigntimeSetDao{

	@Override
	public OfficeSigntimeSet setField(ResultSet rs) throws SQLException{
		OfficeSigntimeSet officeSigntimeSet = new OfficeSigntimeSet();
		officeSigntimeSet.setId(rs.getString("id"));
		officeSigntimeSet.setUnitId(rs.getString("unit_id"));
		officeSigntimeSet.setStartTime(rs.getString("start_time"));
		officeSigntimeSet.setEndTime(rs.getString("end_time"));
		return officeSigntimeSet;
	}

	@Override
	public OfficeSigntimeSet save(OfficeSigntimeSet officeSigntimeSet){
		String sql = "insert into office_signtime_set(id, unit_id, start_time, end_time) values(?,?,?,?)";
		if (StringUtils.isBlank(officeSigntimeSet.getId())){
			officeSigntimeSet.setId(createId());
		}
		Object[] args = new Object[]{
			officeSigntimeSet.getId(), officeSigntimeSet.getUnitId(), 
			officeSigntimeSet.getStartTime(), officeSigntimeSet.getEndTime()
		};
		update(sql, args);
		return officeSigntimeSet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_signtime_set where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSigntimeSet officeSigntimeSet){
		String sql = "update office_signtime_set set unit_id = ?, start_time = ?, end_time = ? where id = ?";
		Object[] args = new Object[]{
			officeSigntimeSet.getUnitId(), officeSigntimeSet.getStartTime(), 
			officeSigntimeSet.getEndTime(), officeSigntimeSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSigntimeSet getOfficeSigntimeSetById(String id){
		String sql = "select * from office_signtime_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSigntimeSet> getOfficeSigntimeSetMapByIds(String[] ids){
		String sql = "select * from office_signtime_set where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetList(){
		String sql = "select * from office_signtime_set";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetPage(Pagination page){
		String sql = "select * from office_signtime_set";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetByUnitIdList(String unitId){
		String sql = "select * from office_signtime_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_signtime_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public OfficeSigntimeSet getOfficeSigntimeSetByUnitIdPage(String unitId,
			String time) {
		StringBuffer sql=new StringBuffer("select * from office_signtime_set where unit_id = ? " +
				"and to_date(?, 'HH24:mi') >= to_date(start_time, 'HH24:mi')"+
			" and to_date(?, 'HH24:mi') <= to_date(end_time, 'HH24:mi')");
		return query(sql.toString(), new Object[]{unitId,time,time }, new SingleRow());
	}
}

