package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeLogDao;
import net.zdsoft.office.dailyoffice.entity.OfficeLog;

/**
 * office_log 
 * @author 
 * 
 */
public class OfficeLogDaoImpl extends BaseDao<OfficeLog> implements OfficeLogDao{
	@Override
	public OfficeLog setField(ResultSet rs) throws SQLException{
		OfficeLog officeLog = new OfficeLog();
		officeLog.setId(rs.getString("id"));
		officeLog.setUnitId(rs.getString("unit_id"));
		officeLog.setUserId(rs.getString("user_id"));
		officeLog.setModid(rs.getString("modid"));
		officeLog.setCode(rs.getString("code"));
		officeLog.setLogtime(rs.getTimestamp("logtime"));
		officeLog.setDescription(rs.getString("description"));
		return officeLog;
	}
	@Override
	public List<OfficeLog> getOfficeList(String unitId,String userId,String modId,String code){
		String sql="select * from office_log where unit_id=? and user_id=? and modId=? ";
		if(StringUtils.isNotEmpty(code)){
			sql+=" and code=?";
			return query(sql, new Object[]{unitId,userId,modId,code},new MultiRow()); 
		}
		return query(sql, new Object[]{unitId,userId,modId},new MultiRow()); 
	}
	public OfficeLog save(OfficeLog officeLog){
		String sql = "insert into office_log(id, unit_id, user_id, modid, code, logtime, description) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeLog.getId())){
			officeLog.setId(createId());
		}
		Object[] args = new Object[]{
			officeLog.getId(), officeLog.getUnitId(), 
			officeLog.getUserId(), officeLog.getModid(), 
			officeLog.getCode(), officeLog.getLogtime(), 
			officeLog.getDescription()
		};
		update(sql, args);
		return officeLog;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_log where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeLog officeLog){
		String sql = "update office_log set unit_id = ?, user_id = ?, modid = ?, code = ?, logtime = ?, description = ? where id = ?";
		Object[] args = new Object[]{
			officeLog.getUnitId(), officeLog.getUserId(), 
			officeLog.getModid(), officeLog.getCode(), 
			officeLog.getLogtime(), officeLog.getDescription(), 
			officeLog.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLog getOfficeLogById(String id){
		String sql = "select * from office_log where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLog> getOfficeLogMapByIds(String[] ids){
		String sql = "select * from office_log where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeLog> getOfficeLogList(){
		String sql = "select * from office_log";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLog> getOfficeLogPage(Pagination page){
		String sql = "select * from office_log";
		return query(sql, new MultiRow(), page);
	}
	

	@Override
	public List<OfficeLog> getOfficeLogByUnitIdList(String unitId){
		String sql = "select * from office_log where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeLog> getOfficeLogByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_log where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}
