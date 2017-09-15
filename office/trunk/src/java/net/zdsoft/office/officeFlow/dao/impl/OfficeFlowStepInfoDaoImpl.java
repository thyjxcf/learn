package net.zdsoft.office.officeFlow.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.officeFlow.entity.OfficeFlowStepInfo;
import net.zdsoft.office.officeFlow.dao.OfficeFlowStepInfoDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_flow_step_info
 * @author 
 * 
 */
public class OfficeFlowStepInfoDaoImpl extends BaseDao<OfficeFlowStepInfo> implements OfficeFlowStepInfoDao{

	@Override
	public OfficeFlowStepInfo setField(ResultSet rs) throws SQLException{
		OfficeFlowStepInfo officeFlowStepInfo = new OfficeFlowStepInfo();
		officeFlowStepInfo.setId(rs.getString("id"));
		officeFlowStepInfo.setFlowId(rs.getString("flow_id"));
		officeFlowStepInfo.setStepId(rs.getString("step_id"));
		officeFlowStepInfo.setTaskUserId(rs.getString("task_user_id"));
		officeFlowStepInfo.setStepType(rs.getString("step_type"));
		return officeFlowStepInfo;
	}

	@Override
	public OfficeFlowStepInfo save(OfficeFlowStepInfo officeFlowStepInfo){
		String sql = "insert into office_flow_step_info(id, flow_id, step_id, task_user_id, step_type) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeFlowStepInfo.getId())){
			officeFlowStepInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeFlowStepInfo.getId(), officeFlowStepInfo.getFlowId(), 
			officeFlowStepInfo.getStepId(), officeFlowStepInfo.getTaskUserId(), 
			officeFlowStepInfo.getStepType()
		};
		update(sql, args);
		return officeFlowStepInfo;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_flow_step_info where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeFlowStepInfo officeFlowStepInfo){
		String sql = "update office_flow_step_info set flow_id = ?, step_id = ?, task_user_id = ?, step_type = ? where id = ?";
		Object[] args = new Object[]{
			officeFlowStepInfo.getFlowId(), officeFlowStepInfo.getStepId(), 
			officeFlowStepInfo.getTaskUserId(), officeFlowStepInfo.getStepType(), 
			officeFlowStepInfo.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeFlowStepInfo getOfficeFlowStepInfoById(String id){
		String sql = "select * from office_flow_step_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeFlowStepInfo> getOfficeFlowStepInfoMapByIds(String[] ids){
		String sql = "select * from office_flow_step_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeFlowStepInfo> getOfficeFlowStepInfoList(){
		String sql = "select * from office_flow_step_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeFlowStepInfo> getOfficeFlowStepInfoPage(Pagination page){
		String sql = "select * from office_flow_step_info";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public void batchRemove(String flowId, String[] stepIds){
		if(stepIds != null){
			String sql = "delete from office_flow_step_info where flow_id = ? and step_id in";
			updateForInSQL(sql, new Object[]{flowId}, stepIds);
		}
		else{
			String sql = "delete from office_flow_step_info where flow_id = ? ";
			update(sql, flowId);
		}
	}
	
	@Override
	public void batchInsert(List<OfficeFlowStepInfo> list){
		String sql = "insert into office_flow_step_info(id, flow_id, step_id, task_user_id, step_type) values(?,?,?,?,?)";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
	    for (int i = 0; i < list.size(); i++) {
	    	OfficeFlowStepInfo officeFlowStepInfo = list.get(i);
	    	if (StringUtils.isBlank(officeFlowStepInfo.getId())){
	    		officeFlowStepInfo.setId(createId());
			}
	        listOfArgs.add(new Object[] {
        		officeFlowStepInfo.getId(), officeFlowStepInfo.getFlowId(), 
        		officeFlowStepInfo.getStepId(), officeFlowStepInfo.getTaskUserId(),
        		officeFlowStepInfo.getStepType()
	        });
	    }
	    int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR };
	    batchUpdate(sql, listOfArgs, argTypes);
	}
	
	@Override
	public List<OfficeFlowStepInfo> getStepInfo(String flowId, String stepId){
		List<Object>list=new ArrayList<Object>();
		String sql = "select * from office_flow_step_info where flow_id = ?";
		list.add(flowId);
		if(StringUtils.isNotBlank(stepId)){
			sql += " and step_id = ?";
			list.add(stepId);
		}
		return query(sql, list.toArray(new Object[0]), new MultiRow());
	}
	
}
