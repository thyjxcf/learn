package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.dailyoffice.entity.RoomorderAuditSms;
import net.zdsoft.office.dailyoffice.dao.RoomorderAuditSmsDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * roomorder_audit_sms
 * @author 
 * 
 */
public class RoomorderAuditSmsDaoImpl extends BaseDao<RoomorderAuditSms> implements RoomorderAuditSmsDao{

	@Override
	public RoomorderAuditSms setField(ResultSet rs) throws SQLException{
		RoomorderAuditSms roomorderAuditSms = new RoomorderAuditSms();
		roomorderAuditSms.setId(rs.getString("id"));
		roomorderAuditSms.setUnitId(rs.getString("unit_id"));
		roomorderAuditSms.setAuditorId(rs.getString("auditor_id"));
		roomorderAuditSms.setNeedSms(rs.getString("need_sms"));
		return roomorderAuditSms;
	}

	@Override
	public RoomorderAuditSms save(RoomorderAuditSms roomorderAuditSms){
		String sql = "insert into office_roomorder_audit_sms(id, unit_id, auditor_id, need_sms) values(?,?,?,?)";
		if (StringUtils.isBlank(roomorderAuditSms.getId())){
			roomorderAuditSms.setId(createId());
		}
		Object[] args = new Object[]{
			roomorderAuditSms.getId(), roomorderAuditSms.getUnitId(), 
			roomorderAuditSms.getAuditorId(), roomorderAuditSms.getNeedSms()
		};
		update(sql, args);
		return roomorderAuditSms;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_roomorder_audit_sms where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer deleteByUserId(String unitId, String userId){
		String sql = "delete from office_roomorder_audit_sms where unit_id = ? and auditor_id = ?";
		return update(sql, new Object[]{unitId, userId});
	}

	@Override
	public Integer update(RoomorderAuditSms roomorderAuditSms){
		String sql = "update office_roomorder_audit_sms set unit_id = ?, auditor_id = ?, need_sms = ? where id = ?";
		Object[] args = new Object[]{
			roomorderAuditSms.getUnitId(), roomorderAuditSms.getAuditorId(), 
			roomorderAuditSms.getNeedSms(), roomorderAuditSms.getId()
		};
		return update(sql, args);
	}

	@Override
	public RoomorderAuditSms getRoomorderAuditSmsById(String id){
		String sql = "select * from office_roomorder_audit_sms where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, RoomorderAuditSms> getRoomorderAuditSmsMapByIds(String[] ids){
		String sql = "select * from office_roomorder_audit_sms where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsList(){
		String sql = "select * from office_roomorder_audit_sms";
		return query(sql, new MultiRow());
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsPage(Pagination page){
		String sql = "select * from office_roomorder_audit_sms";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsByUnitIdList(String unitId){
		String sql = "select * from office_roomorder_audit_sms where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_roomorder_audit_sms where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public RoomorderAuditSms getRoomorderAuditSmsByUserId(String unitId, String userId){
		String sql= "select * from office_roomorder_audit_sms where unit_id = ? and auditor_id = ?";
		return query(sql, new Object[]{unitId, userId}, new SingleRow());
	}
	
	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsList(String unitId){
		String sql = "select * from office_roomorder_audit_sms where unit_id = ? and need_sms = 1";
		return query(sql, new Object[]{unitId}, new MultiRow());
	}
}