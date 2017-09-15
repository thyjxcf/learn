package net.zdsoft.office.dailyoffice.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkArrangeContentDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeContent;

import org.apache.commons.lang.StringUtils;
/**
 * office_work_arrange_content
 * @author 
 * 
 */
public class OfficeWorkArrangeContentDaoImpl extends BaseDao<OfficeWorkArrangeContent> implements OfficeWorkArrangeContentDao{

	private static final String SQL_INSERT = "insert into office_work_arrange_content(id, outline_id, detail_id, work_date, content,arrangContent, dept_ids, place, state, work_start_time, work_End_time, attendees) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	@Override
	public OfficeWorkArrangeContent setField(ResultSet rs) throws SQLException{
		OfficeWorkArrangeContent officeWorkArrangeContent = new OfficeWorkArrangeContent();
		officeWorkArrangeContent.setId(rs.getString("id"));
		officeWorkArrangeContent.setOutlineId(rs.getString("outline_id"));
		officeWorkArrangeContent.setDetailId(rs.getString("detail_id"));
		officeWorkArrangeContent.setWorkDate(rs.getTimestamp("work_date"));
		officeWorkArrangeContent.setContent(rs.getString("content"));
		officeWorkArrangeContent.setArrangContent(rs.getString("arrangContent"));
		officeWorkArrangeContent.setDeptIds(rs.getString("dept_ids"));
		officeWorkArrangeContent.setPlace(rs.getString("place"));
		officeWorkArrangeContent.setState(rs.getString("state"));
		officeWorkArrangeContent.setWorkStartTime(rs.getString("work_start_time"));
		officeWorkArrangeContent.setWorkEndTime(rs.getString("work_End_time"));
		officeWorkArrangeContent.setAttendees(rs.getString("attendees"));
		return officeWorkArrangeContent;
	}

	@Override
	public OfficeWorkArrangeContent save(OfficeWorkArrangeContent officeWorkArrangeContent){
		
		if (StringUtils.isBlank(officeWorkArrangeContent.getId())){
			officeWorkArrangeContent.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkArrangeContent.getId(), officeWorkArrangeContent.getOutlineId(), 
			officeWorkArrangeContent.getDetailId(), officeWorkArrangeContent.getWorkDate(), 
			officeWorkArrangeContent.getContent(),officeWorkArrangeContent.getArrangContent(), officeWorkArrangeContent.getDeptIds(), 
			officeWorkArrangeContent.getPlace(), officeWorkArrangeContent.getState(),
			officeWorkArrangeContent.getWorkStartTime(), officeWorkArrangeContent.getWorkEndTime(),
			officeWorkArrangeContent.getAttendees()
		};
		update(SQL_INSERT, args);
		return officeWorkArrangeContent;
	}
	
	@Override
	public void batchSave(List<OfficeWorkArrangeContent> owacList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeWorkArrangeContent officeWorkArrangeContent:owacList) {
			if (StringUtils.isBlank(officeWorkArrangeContent.getId()))
				officeWorkArrangeContent.setId(getGUID());
			Object[] args = new Object[]{
				officeWorkArrangeContent.getId(), officeWorkArrangeContent.getOutlineId(), 
				officeWorkArrangeContent.getDetailId(), officeWorkArrangeContent.getWorkDate(), 
				officeWorkArrangeContent.getContent(),officeWorkArrangeContent.getArrangContent(), officeWorkArrangeContent.getDeptIds(), 
				officeWorkArrangeContent.getPlace(), officeWorkArrangeContent.getState(),
				officeWorkArrangeContent.getWorkStartTime(), officeWorkArrangeContent.getWorkEndTime(),
				officeWorkArrangeContent.getAttendees()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR,Types.CHAR,
				Types.DATE, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_arrange_content where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public void deleteByDetailId(String detailId) {
		String sql = "delete from office_work_arrange_content where detail_id = ? ";
		update(sql, new Object[]{detailId});
	}

	@Override
	public Integer update(OfficeWorkArrangeContent officeWorkArrangeContent){
		String sql = "update office_work_arrange_content set work_date = ?, content = ?,arrangContent=?, dept_ids = ?, place = ?, work_start_time = ?, work_end_time = ?, attendees = ? where id = ? ";
		Object[] args = new Object[]{
			officeWorkArrangeContent.getWorkDate(), officeWorkArrangeContent.getContent(), officeWorkArrangeContent.getArrangContent(),
			officeWorkArrangeContent.getDeptIds(), officeWorkArrangeContent.getPlace(),
			officeWorkArrangeContent.getWorkStartTime(), officeWorkArrangeContent.getWorkEndTime(),
			officeWorkArrangeContent.getAttendees(), officeWorkArrangeContent.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void updateStateByOutLineId(String outLineId, String state) {
		String sql = "update office_work_arrange_content set state = ? where outline_id = ? and state > 1 ";
		Object[] args = new Object[]{state, outLineId};
		update(sql, args);
	}

	@Override
	public OfficeWorkArrangeContent getOfficeWorkArrangeContentById(String id){
		String sql = "select * from office_work_arrange_content where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkArrangeContent> getOfficeWorkArrangeContentMapByIds(String[] ids){
		String sql = "select * from office_work_arrange_content where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentListByDetailId(String detailId){
		String sql = "select * from office_work_arrange_content where detail_id = ? ";
		return query(sql, new Object[]{detailId}, new MultiRow());
	}
	
	@Override
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentListByOutLineId(
			String outLineId, String state) {
		String sql = "select * from office_work_arrange_content where outline_id = ? and state = ? ";
		return query(sql, new Object[]{outLineId,state}, new MultiRow());
	}
	
	@Override
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentListByOutLineId(
			String outLineId, String deptId, String state) {
		String sql = "select * from office_work_arrange_content where outline_id = ? and state = ? ";
		if(StringUtils.isNotBlank(deptId)){
			sql += " and dept_ids like ? ";
			return query(sql, new Object[]{outLineId,state, "%"+deptId+"%"}, new MultiRow());
		}else{
			return query(sql, new Object[]{outLineId,state}, new MultiRow());
		}
	}

	@Override
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentPage(Pagination page){
		String sql = "select * from office_work_arrange_content";
		return query(sql, new MultiRow(), page);
	}
}