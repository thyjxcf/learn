package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveMeetMinutesDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetMinutes;

import org.apache.commons.lang.StringUtils;
/**
 * office_executive_meet_minutes
 * @author 
 * 
 */
public class OfficeExecutiveMeetMinutesDaoImpl extends BaseDao<OfficeExecutiveMeetMinutes> implements OfficeExecutiveMeetMinutesDao{

	@Override
	public OfficeExecutiveMeetMinutes setField(ResultSet rs) throws SQLException{
		OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes = new OfficeExecutiveMeetMinutes();
		officeExecutiveMeetMinutes.setId(rs.getString("id"));
		officeExecutiveMeetMinutes.setMeetingId(rs.getString("meeting_id"));
		officeExecutiveMeetMinutes.setContent(rs.getString("content"));
		officeExecutiveMeetMinutes.setDeptIds(rs.getString("dept_ids"));
		officeExecutiveMeetMinutes.setCreateTime(rs.getTimestamp("create_time"));
		officeExecutiveMeetMinutes.setUnitId(rs.getString("unit_id"));
		return officeExecutiveMeetMinutes;
	}

	@Override
	public OfficeExecutiveMeetMinutes save(OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes){
		String sql = "insert into office_executive_meet_minutes(id, meeting_id, content, dept_ids, create_time, unit_id) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExecutiveMeetMinutes.getId())){
			officeExecutiveMeetMinutes.setId(createId());
		}
		Object[] args = new Object[]{
			officeExecutiveMeetMinutes.getId(), officeExecutiveMeetMinutes.getMeetingId(), 
			officeExecutiveMeetMinutes.getContent(), officeExecutiveMeetMinutes.getDeptIds(), 
			officeExecutiveMeetMinutes.getCreateTime(), officeExecutiveMeetMinutes.getUnitId()
		};
		update(sql, args);
		return officeExecutiveMeetMinutes;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_executive_meet_minutes where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes){
		String sql = "update office_executive_meet_minutes set meeting_id = ?, content = ?, dept_ids = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveMeetMinutes.getMeetingId(), officeExecutiveMeetMinutes.getContent(), 
			officeExecutiveMeetMinutes.getDeptIds(), officeExecutiveMeetMinutes.getCreateTime(), 
			officeExecutiveMeetMinutes.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExecutiveMeetMinutes getOfficeExecutiveMeetMinutesById(String id){
		String sql = "select * from office_executive_meet_minutes where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesMapByIds(String[] ids){
		String sql = "select * from office_executive_meet_minutes where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesList(String deptId, String meetId){
		
		if(StringUtils.isNotBlank(deptId)){
			String sql = "select * from office_executive_meet_minutes where meeting_id = ? and dept_ids like ? ";
			return query(sql, new Object[]{meetId,"%"+deptId+"%"}, new MultiRow());
		}else{
			String sql = "select * from office_executive_meet_minutes where meeting_id = ? ";
			return query(sql, new Object[]{meetId}, new MultiRow());
		}
	}

	@Override
	public List<OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesPage(Pagination page){
		String sql = "select * from office_executive_meet_minutes";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public void batchSave(List<OfficeExecutiveMeetMinutes> list) {
		String sql = "insert into office_executive_meet_minutes(id, meeting_id, content, dept_ids, create_time, unit_id) values(?,?,?,?,?,?)";
		List<Object[]> objs = new ArrayList<Object[]>();
		for(OfficeExecutiveMeetMinutes minutes:list){
			if (StringUtils.isBlank(minutes.getId())){
				minutes.setId(createId());
			}
			Object[] args = new Object[]{minutes.getId(),
					minutes.getMeetingId(),minutes.getContent(),
					minutes.getDeptIds(),minutes.getCreateTime(),
					minutes.getUnitId()};
			objs.add(args);
		}
		batchUpdate(sql, objs, new int[] {Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.CHAR});
	}
	
	@Override
	public void deleteByMeetId(String meetId) {
		String sql = "delete from office_executive_meet_minutes where meeting_id = ? ";
		update(sql, new Object[]{meetId});
	}
	
	@Override
	public Map<String, String> getMinutesMap(String unitId, String[] meetIds, String deptId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct meeting_id from office_executive_meet_minutes where unit_id = ? ");
		List<Object> objects = new ArrayList<Object>();
		objects.add(unitId);
		if(StringUtils.isNotBlank(deptId)){
			sql.append(" and dept_ids like ? ");
			objects.add("%"+deptId+"%");
		}
		sql.append(" and meeting_id in ");
		return queryForInSQL(sql.toString(), objects.toArray(), meetIds, new MapRowMapper<String, String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("meeting_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("meeting_id");
			}
		});
	}
}