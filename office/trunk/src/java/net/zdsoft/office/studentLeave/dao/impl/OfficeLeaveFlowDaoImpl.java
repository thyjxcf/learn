package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.studentLeave.entity.OfficeLeaveFlow;
import net.zdsoft.office.studentLeave.dao.OfficeLeaveFlowDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_leave_flow
 * @author 
 * 
 */
public class OfficeLeaveFlowDaoImpl extends BaseDao<OfficeLeaveFlow> implements OfficeLeaveFlowDao{

	@Override
	public OfficeLeaveFlow setField(ResultSet rs) throws SQLException{
		OfficeLeaveFlow officeLeaveFlow = new OfficeLeaveFlow();
		officeLeaveFlow.setId(rs.getString("id"));
		officeLeaveFlow.setLeaveType(rs.getString("leave_type"));
		officeLeaveFlow.setFlowId(rs.getString("flow_id"));
		officeLeaveFlow.setUnitId(rs.getString("unit_id"));
		officeLeaveFlow.setGradeId(rs.getString("grade_id"));
		return officeLeaveFlow;
	}

	@Override
	public OfficeLeaveFlow save(OfficeLeaveFlow officeLeaveFlow){
		String sql = "insert into office_leave_flow(id, leave_type, flow_id,unit_id,grade_id) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeLeaveFlow.getId())){
			officeLeaveFlow.setId(createId());
		}
		Object[] args = new Object[]{
			officeLeaveFlow.getId(), officeLeaveFlow.getLeaveType(), 
			officeLeaveFlow.getFlowId(),officeLeaveFlow.getUnitId(),
			officeLeaveFlow.getGradeId()
		};
		update(sql, args);
		return officeLeaveFlow;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_leave_flow where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeLeaveFlow officeLeaveFlow){
		String sql = "update office_leave_flow set leave_type = ?, flow_id = ?,unit_id = ?,grade_id=? where id = ?";
		Object[] args = new Object[]{
			officeLeaveFlow.getLeaveType(), officeLeaveFlow.getFlowId(), officeLeaveFlow.getUnitId(),officeLeaveFlow.getGradeId(),
			officeLeaveFlow.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLeaveFlow getOfficeLeaveFlowById(String id){
		String sql = "select * from office_leave_flow where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLeaveFlow> getOfficeLeaveFlowMapByIds(String[] ids){
		String sql = "select * from office_leave_flow where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeLeaveFlow> getOfficeLeaveFlowList(){
		String sql = "select * from office_leave_flow";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLeaveFlow> getOfficeLeaveFlowPage(Pagination page){
		String sql = "select * from office_leave_flow";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeLeaveFlow> getOfficeLeaveFlows(String unitId,String type) {
		String sql="select * from office_leave_flow where unit_id=? and leave_type=?";
		return query(sql, new Object[]{unitId,type}, new MultiRow());
	}
	@Override
	public List<OfficeLeaveFlow> getFlowsByGradeIdAndType(String unitId,
			String gradeId, String type) {
		String sql="select * from office_leave_flow where unit_id=? and leave_type=? and grade_id=?";
		return query(sql, new Object[]{unitId,type,gradeId}, new MultiRow());
	}
	@Override
	public void batchSave(List<OfficeLeaveFlow> officeLeaveFlows) {
		String sql = "insert into office_leave_flow(id, leave_type, flow_id,unit_id,grade_id) values(?,?,?,?,?)";
		List<Object[]> objects=new ArrayList<Object[]>();
		for (OfficeLeaveFlow officeLeaveFlow : officeLeaveFlows) {
			if(StringUtils.isBlank(officeLeaveFlow.getId())){
				officeLeaveFlow.setId(getGUID());
			}
			Object[] object=new Object[]{officeLeaveFlow.getId(),officeLeaveFlow.getLeaveType(),
					officeLeaveFlow.getFlowId(),officeLeaveFlow.getUnitId(),officeLeaveFlow.getGradeId()};
			objects.add(object);
		}
		int[] argTypes=new int[]{Types.CHAR,Types.VARCHAR,Types.CHAR,
				Types.CHAR,Types.CHAR};
		batchUpdate(sql, objects, argTypes);
	}

	@Override
	public void deleteOfficeLeaveFlowByType(String unitId,String gradeId, String type) {
		String sql = "delete from office_leave_flow where unit_id=? and leave_type=? and grade_id=?";
		update(sql, new Object[]{unitId,type,gradeId});
	}
	
}
