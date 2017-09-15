package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.office.dailyoffice.dao.OfficeRoomOrderSetDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_room_order_set
 * @author 
 * 
 */
public class OfficeRoomOrderSetDaoImpl extends BaseDao<OfficeRoomOrderSet> implements OfficeRoomOrderSetDao{

	@Override
	public OfficeRoomOrderSet setField(ResultSet rs) throws SQLException{
		OfficeRoomOrderSet officeRoomOrderSet = new OfficeRoomOrderSet();
		officeRoomOrderSet.setId(rs.getString("id"));
		officeRoomOrderSet.setThisId(rs.getString("this_id"));
		officeRoomOrderSet.setUseType(rs.getString("use_type"));
		officeRoomOrderSet.setNeedAudit(rs.getString("need_audit"));
		officeRoomOrderSet.setUnitId(rs.getString("unit_id"));
		officeRoomOrderSet.setIsSelected(rs.getString("is_selected"));
		return officeRoomOrderSet;
	}

	@Override
	public OfficeRoomOrderSet save(OfficeRoomOrderSet officeRoomOrderSet){
		String sql = "insert into office_room_order_set(id, this_id, use_type, need_audit, unit_id,is_selected) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeRoomOrderSet.getId())){
			officeRoomOrderSet.setId(createId());
		}
		Object[] args = new Object[]{
			officeRoomOrderSet.getId(), officeRoomOrderSet.getThisId(), 
			officeRoomOrderSet.getUseType(), officeRoomOrderSet.getNeedAudit(), 
			officeRoomOrderSet.getUnitId(),officeRoomOrderSet.getIsSelected()
		};
		update(sql, args);
		return officeRoomOrderSet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_room_order_set where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeRoomOrderSet officeRoomOrderSet){
		String sql = "update office_room_order_set set this_id = ?, use_type = ?, need_audit = ?, unit_id = ?, is_selected=? where id = ?";
		Object[] args = new Object[]{
			officeRoomOrderSet.getThisId(), officeRoomOrderSet.getUseType(), 
			officeRoomOrderSet.getNeedAudit(), officeRoomOrderSet.getUnitId(),
			officeRoomOrderSet.getIsSelected(),officeRoomOrderSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeRoomOrderSet getOfficeRoomOrderSetById(String id){
		String sql = "select * from office_room_order_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeRoomOrderSet> getOfficeRoomOrderSetMapByIds(String[] ids){
		String sql = "select * from office_room_order_set where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetList(){
		String sql = "select * from office_room_order_set";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetPage(Pagination page){
		String sql = "select * from office_room_order_set";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdList(String unitId){
		String sql = "select * from office_room_order_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_room_order_set where unit_id = ?";
		if(page != null)
			return query(sql, new Object[]{unitId }, new MultiRow(), page);
		else
			return query(sql, new Object[]{unitId }, new MultiRow());
	}
	
	@Override
	public OfficeRoomOrderSet getOfficeRoomOrderSetByThisId(String thisId,
			String unitId) {
		String sql = "select * from office_room_order_set where this_Id = ? and unit_Id=?";
		return query(sql, new Object[]{thisId,unitId }, new SingleRow());
	}
	@Override
	public OfficeRoomOrderSet getOfficeRoomOrderSetBySelect(String unitId) {
		String sql = "select * from office_room_order_set where is_selected=1 and unit_Id=?";
		return query(sql, new Object[]{unitId }, new SingleRow());
	}
	
	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByType(String unitId, String type){
		String sql = "select * from office_room_order_set where unit_id = ? and this_id = ?";
		return query(sql, new Object[]{unitId, type}, new MultiRow());
	}
}
