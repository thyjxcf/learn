package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkArrangeOutlineDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeOutline;

import org.apache.commons.lang.StringUtils;

/**
 * office_work_arrange_outline 
 * @author 
 * 
 */
public class OfficeWorkArrangeOutlineDaoImpl extends BaseDao<OfficeWorkArrangeOutline> implements OfficeWorkArrangeOutlineDao{
	
	private static final String FIND_WORK_OUTLINES = "select * from office_work_arrange_outline where unit_id = ? "
			+ " and to_char(start_time,'yyyy') = ?  order by start_time desc ";
	private static final String FIND_WORK_OUTLINES_SCHOOL = "select * from office_work_arrange_outline where unit_id = ? "
			+ " and acadyear = ? and semester = ? order by start_time desc ";
	
	private static final String FIND_WORK_OUTLINES_BY_TIME = "select * from office_work_arrange_outline where unit_id = ? "
			+ " and start_time = ? and end_time = ?";
	
	private static final String FIND_WORK_OUTLINES_EDU_STATE = "select * from office_work_arrange_outline where unit_id = ? "
			+ " and to_char(start_time,'yyyy') = ? and state = ? order by start_time desc ";
	private static final String FIND_WORK_OUTLINES_SCHOOL_STATE = "select * from office_work_arrange_outline where unit_id = ? "
			+ " and acadyear = ? and semester = ? and state = ? order by start_time desc ";
	
	private static final String FIND_WORK_OUTLINEID_SCHOOL_STATE = "select id from office_work_arrange_outline where unit_id = ? "
			+ " and acadyear = ? and semester = ? and state = ? and start_time <= to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') and end_time >= to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ";
	
	private static final String FIND_WORK_OUTLINEID_EDU_STATE = "select id from office_work_arrange_outline where unit_id = ? "
			+ " and state = ? and start_time <= to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') and end_time >= to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ";
	
	
	@Override
	public OfficeWorkArrangeOutline setField(ResultSet rs) throws SQLException {
		OfficeWorkArrangeOutline workOutline = new OfficeWorkArrangeOutline();
    	workOutline.setId(rs.getString("id"));
    	workOutline.setName(rs.getString("name"));
    	workOutline.setWorkContent(rs.getString("work_content"));
    	workOutline.setUnitId(rs.getString("unit_id"));
    	workOutline.setStartTime(rs.getTimestamp("start_time"));
    	workOutline.setEndTime(rs.getTimestamp("end_time"));
    	workOutline.setCreateTime(rs.getTimestamp("create_time"));
    	workOutline.setCreateUserId(rs.getString("create_user_id"));
    	workOutline.setAcadyear(rs.getString("acadyear"));
    	workOutline.setSemester(rs.getString("semester"));
    	workOutline.setState(rs.getString("state"));
        return workOutline;
	}
	
	public OfficeWorkArrangeOutline save(OfficeWorkArrangeOutline workOutline){
		String sql = "insert into office_work_arrange_outline(id, name, work_content, unit_id, start_time, end_time, create_time, create_user_id,acadyear,semester,state) values(?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(workOutline.getId())){
			workOutline.setId(createId());
		}
		Object[] args = new Object[]{
				workOutline.getId(), workOutline.getName(), 
				workOutline.getWorkContent(), workOutline.getUnitId(), 
				workOutline.getStartTime(), workOutline.getEndTime(), 
				workOutline.getCreateTime(), workOutline.getCreateUserId(),
				workOutline.getAcadyear(), workOutline.getSemester(),
				workOutline.getState()
		};
		update(sql, args);
		return workOutline;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_arrange_outline where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeWorkArrangeOutline workOutline){
		String sql = "update office_work_arrange_outline set name = ?, work_content = ?, start_time = ?, end_time = ? where id = ?";
		Object[] args = new Object[]{
				workOutline.getName(), workOutline.getWorkContent(), 
				workOutline.getStartTime(), workOutline.getEndTime(), 
				workOutline.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void updateState(String id, String state) {
		String sql = "update office_work_arrange_outline set state = ? where id = ? ";
		Object[] args = new Object[]{state, id};
		update(sql, args);
	}

	@Override
	public OfficeWorkArrangeOutline getOfficeWorkArrangeOutlineById(String id){
		String sql = "select * from office_work_arrange_outline where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlines(String unitId, String year,
		  Pagination page) {
		if(page != null) {
			return query(FIND_WORK_OUTLINES, new Object[] {unitId, year}, new MultiRow(), page);
		}
		return query(FIND_WORK_OUTLINES, new Object[] {unitId, year}, new MultiRow());
	}
	
	@Override
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlines(
			String unitId, String acadyear, String semester, Pagination page) {
		if(page != null) {
			return query(FIND_WORK_OUTLINES_SCHOOL, new Object[] {unitId, acadyear, semester}, new MultiRow(), page);
		}
		return query(FIND_WORK_OUTLINES_SCHOOL, new Object[] {unitId, acadyear, semester}, new MultiRow());
	}
	
	@Override
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlineList(
			String unitId, String acadyear, String semester, String state) {
		return query(FIND_WORK_OUTLINES_SCHOOL_STATE, new Object[] {unitId, acadyear, semester, state}, new MultiRow());
	}
	
	@Override
	public String getOfficeWorkArrangeOutline(String unitId, String acadyear,
			String semester, String state) {
		return query(FIND_WORK_OUTLINEID_SCHOOL_STATE, new Object[] {unitId, acadyear, semester, state}, new SingleRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs) throws SQLException {
				return rs.getString("id");
			}
		});
	}
	
	@Override
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlineList(
			String unitId, String year, String state) {
		return query(FIND_WORK_OUTLINES_EDU_STATE, new Object[] {unitId, year, state}, new MultiRow());
	}
	
	@Override
	public String getOfficeWorkArrangeOutline(String unitId,
			String state) {
		return query(FIND_WORK_OUTLINEID_EDU_STATE, new Object[] {unitId, state}, new SingleRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs) throws SQLException {
				return rs.getString("id");
			}
		});
	}
	
	@Override
	public boolean isExistConflict(String unitId, String id,
			Date startTime, Date endTime) {
		String sql = "select count(1) from office_work_arrange_outline where unit_id = ? and ((start_time <= ? and end_time >= ?) or (start_time <= ? and end_time >= ?) or (start_time >= ? and end_time <= ?))";
		int i = 0;
		if(StringUtils.isNotBlank(id)){
			sql += " and id <> ? ";
			i = queryForInt(sql, new Object[] {unitId, startTime, startTime, endTime, endTime, startTime, endTime, id});
		}else{
			i = queryForInt(sql, new Object[] {unitId, startTime, startTime, endTime, endTime, startTime, endTime});
		}
		if(i > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public OfficeWorkArrangeOutline getOfficeWorkArrangeOutlines(String unitId, Date startTime, Date endTime) {
		return query(FIND_WORK_OUTLINES_BY_TIME, new Object[] {unitId, startTime, endTime}, new SingleRow());
	}

}
