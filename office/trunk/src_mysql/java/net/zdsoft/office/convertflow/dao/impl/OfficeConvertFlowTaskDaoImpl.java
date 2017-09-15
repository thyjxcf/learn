package net.zdsoft.office.convertflow.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.dao.OfficeConvertFlowTaskDao;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlowTask;
/**
 * office_convert_flow_task
 * @author 
 * 
 */
public class OfficeConvertFlowTaskDaoImpl extends BaseDao<OfficeConvertFlowTask> implements OfficeConvertFlowTaskDao{
	
	private static final String	INSERT_SQL = "insert into office_convert_flow_task(id, convert_flow_id, audit_parm, status, parm, create_time) values(?,?,?,?,?,?)";
	private static final String UPDATE_SQL = "update office_convert_flow_task set convert_flow_id = ?, audit_parm = ?, status = ?, parm = ?, create_time = ? where id = ?";
	private static final String UPDATE_SQL_BY_PARM = "update office_convert_flow_task set audit_parm = ? , status = ? where parm = ?";
	private static final String UPDATE_SQL_BY_CONVERT_FLOW_ID = "update office_convert_flow_task set status = ? where convert_flow_id = ?";

	@Override
	public OfficeConvertFlowTask setField(ResultSet rs) throws SQLException{
		OfficeConvertFlowTask officeConvertFlowTask = new OfficeConvertFlowTask();
		officeConvertFlowTask.setId(rs.getString("id"));
		officeConvertFlowTask.setConvertFlowId(rs.getString("convert_flow_id"));
		officeConvertFlowTask.setAuditParm(rs.getString("audit_parm"));
		officeConvertFlowTask.setStatus(rs.getInt("status"));
		officeConvertFlowTask.setParm(rs.getString("parm"));
		officeConvertFlowTask.setCreateTime(rs.getTimestamp("create_time"));
		return officeConvertFlowTask;
	}
	
	public void save(OfficeConvertFlowTask ent){
		List<Object[]> args = new ArrayList<Object[]>();
			if (StringUtils.isBlank(ent.getId())){
				ent.setId(createId());
			}
			if(ent.getCreateTime() == null){
				ent.setCreateTime(new Date());
			}
		
			update(INSERT_SQL, new Object[]{
					ent.getId(), ent.getConvertFlowId(), 
					ent.getAuditParm(), ent.getStatus(), 
					 ent.getParm(), 
					ent.getCreateTime()
			});
	}
	
	public void batchInsert(List<OfficeConvertFlowTask> list){
		List<Object[]> args = new ArrayList<Object[]>();
		for(OfficeConvertFlowTask ent : list){
			if (StringUtils.isBlank(ent.getId())){
				ent.setId(createId());
			}
			if(ent.getCreateTime() == null){
				ent.setCreateTime(new Date());
			}
			args.add(new Object[]{
					ent.getId(), ent.getConvertFlowId(), 
					ent.getAuditParm(), ent.getStatus(), 
					ent.getParm(), 
					ent.getCreateTime()
			});
		}
		
		batchUpdate(INSERT_SQL, args, new int[]{
				Types.CHAR,Types.CHAR, 
				Types.CHAR, Types.INTEGER, 
				Types.VARCHAR, 
				Types.TIMESTAMP
		});
	}

	@Override
	public Integer update(OfficeConvertFlowTask officeConvertFlowTask){
		Object[] args = new Object[]{
			officeConvertFlowTask.getConvertFlowId(), officeConvertFlowTask.getAuditParm(), 
			officeConvertFlowTask.getStatus(), 
			officeConvertFlowTask.getParm(), officeConvertFlowTask.getCreateTime(), 
			officeConvertFlowTask.getId()
		};
		return update(UPDATE_SQL, args);
	}

	public void updateByParm(String auditUserId, int status, String parm){
		update(UPDATE_SQL_BY_PARM, new Object[]{auditUserId, status, parm});
	}
	
	public void updateByConvertFlowId(int status, String convertFlowId){
		update(UPDATE_SQL_BY_CONVERT_FLOW_ID, new Object[]{status, convertFlowId});
	}
	@Override
	public int deleteByConvertFlowId(String convertFlowId) {
		String sql = "delete from office_convert_flow_task where convert_flow_id in";
		return updateForInSQL(sql, null, new String[]{convertFlowId});
	}
}