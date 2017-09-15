package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkArrangeDetailDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeDetail;

import org.apache.commons.lang.StringUtils;

/**
 * office_outline_content 
 * @author 
 * 
 */
public class OfficeWorkArrangeDetailDaoImpl extends BaseDao<OfficeWorkArrangeDetail> implements OfficeWorkArrangeDetailDao{
	
	private static final String FIND_BY_DEPTID = "select * from office_work_arrange_detail where outline_id = ? and dept_id = ?";
	private static final String DELETE_BY_DEPTID = "delete from office_work_arrange_detail where outline_id = ? and dept_id = ? and state = ?";
	private static final String FIND_BY_OUTLINEID = "select count(1) from office_work_arrange_detail where outline_id = ?";
	
	@Override
	public OfficeWorkArrangeDetail setField(ResultSet rs) throws SQLException {
		OfficeWorkArrangeDetail workOutlineDetail = new OfficeWorkArrangeDetail();
		workOutlineDetail.setId(rs.getString("id"));
		workOutlineDetail.setRemark(rs.getString("remark"));
		workOutlineDetail.setState(rs.getString("state"));
		workOutlineDetail.setOutlineId(rs.getString("outline_id"));
		workOutlineDetail.setDeptId(rs.getString("dept_id"));
		workOutlineDetail.setCreateUserId(rs.getString("create_user_id"));
		workOutlineDetail.setUnitId(rs.getString("unit_id"));
		return workOutlineDetail;
	}
	
	public OfficeWorkArrangeDetail save(OfficeWorkArrangeDetail officeWorkArrangeDetail){
		String sql = "insert into office_work_arrange_detail(id, remark, state, outline_id, dept_id, create_user_id, unit_id) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeWorkArrangeDetail.getId())){
			officeWorkArrangeDetail.setId(createId());
		}
		Object[] args = new Object[]{
				officeWorkArrangeDetail.getId(), officeWorkArrangeDetail.getRemark(), 
				officeWorkArrangeDetail.getState(), officeWorkArrangeDetail.getOutlineId(), 
				officeWorkArrangeDetail.getDeptId(), officeWorkArrangeDetail.getCreateUserId(), 
				officeWorkArrangeDetail.getUnitId()
		};
		update(sql, args);
		return officeWorkArrangeDetail;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_arrange_detail where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer delete(String outlineId, String deptId, String state) {
		return update(DELETE_BY_DEPTID, new Object[] {outlineId, deptId, state});
	}
	
	@Override
	public Integer update(OfficeWorkArrangeDetail officeWorkArrangeDetail){
		String sql = "update office_work_arrange_detail set remark = ?, state = ? where id = ?";
		Object[] args = new Object[]{
				officeWorkArrangeDetail.getRemark(), officeWorkArrangeDetail.getState(),
				officeWorkArrangeDetail.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void updateStateByOutLineId(String outLineId, String state) {
		String sql = "update office_work_arrange_detail set state = ? where outline_id = ? and state > 1";
		Object[] args = new Object[]{state, outLineId};
		update(sql, args);
	}
	
	@Override
	public void updateRemark(String id, String remark) {
		String sql = "update office_work_arrange_detail set remark = ? where id = ? ";
		Object[] args = new Object[]{remark, id};
		update(sql, args);
	}

	@Override
	public OfficeWorkArrangeDetail getOfficeWorkArrangeDetailById(String id){
		String sql = "select * from office_work_arrange_detail where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public boolean isExistDetailByOutlineId(String outlineId) {
		int i = queryForInt(FIND_BY_OUTLINEID, new Object[] {outlineId});
		if(i > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public Map<String, OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailMap(
			String[] outLineIds, String deptId) {
		String sql = "select * from office_work_arrange_detail where dept_id = ? and outline_id in ";
		return queryForInSQL(sql, new Object[]{deptId}, outLineIds, new MapRowMapper<String, OfficeWorkArrangeDetail>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("outline_id");
			}
			@Override
			public OfficeWorkArrangeDetail mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeWorkArrangeDetail officeWorkArrangeDetail = setField(rs);
				return officeWorkArrangeDetail;
			}
		});
	}
	
	@Override
	public OfficeWorkArrangeDetail getOfficeWorkArrangeDetail(String outlineId,
			String deptId) {
		 return query(FIND_BY_DEPTID, new Object[]{outlineId, deptId}, new SingleRow());
	}
	
	@Override
	public List<OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailListByOutLineId(
			String outlineId, String state) {
		String sql = "select * from office_work_arrange_detail where outline_id = ? and state = ? ";
		return query(sql, new Object[]{outlineId, state}, new MultiRow());
	}
	
	@Override
	public List<OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailListByOutLineId(
			String outlineId, String deptId, String state) {
		String sql = "select * from office_work_arrange_detail where outline_id = ? and state = ? ";
		if(StringUtils.isNotBlank(deptId)){
			sql += " and dept_id = ? ";
			return query(sql, new Object[]{outlineId, state, deptId}, new MultiRow());
		}else{
			return query(sql, new Object[]{outlineId, state}, new MultiRow());
		}
	}

	@Override
	public List<OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailList(
			String unitId, String year, String workOutlineId, String deptId,
			String state, Pagination page) {
		StringBuffer sbf = new StringBuffer("select a.* from office_work_arrange_detail a inner join office_work_arrange_outline b on a.outline_id = b.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		sbf.append(" and a.unit_id = ?");
		args.add(unitId);
		sbf.append(" and (to_char(b.start_time,'yyyy') = ? or to_char(b.end_time, 'yyyy') = ?)");
		args.add(year);
		args.add(year);
		if(StringUtils.isNotBlank(workOutlineId)) {
			sbf.append(" and a.outline_id = ?");
			args.add(workOutlineId);
		}
		if(StringUtils.isNotBlank(deptId)) {
			sbf.append(" and a.dept_id = ?");
			args.add(deptId);
		}
		if(StringUtils.isNotBlank(state)){
			sbf.append(" and a.state = ?");
			args.add(state);
		}
		sbf.append(" order by b.start_time desc");
		if(page != null) {
			return query(sbf.toString(), args.toArray(), new MultiRow(), page);
		}
		return query(sbf.toString(), args.toArray(), new MultiRow());
	}
	
	@Override
	public Map<String, String> getOutLineIdsMap(String[] outLineIds) {
		String sql = "select distinct outline_id from office_work_arrange_detail where outline_id in ";
		return queryForInSQL(sql, null, outLineIds, new MapRowMapper<String, String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("outline_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("outline_id");
			}
		});
	}
}
