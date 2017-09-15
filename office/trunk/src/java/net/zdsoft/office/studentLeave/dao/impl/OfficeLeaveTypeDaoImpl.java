package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeLeaveTypeDao;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;

import org.apache.commons.lang.StringUtils;
/**
 * office_leave_type
 * @author 
 * 
 */
public class OfficeLeaveTypeDaoImpl extends BaseDao<OfficeLeaveType> implements OfficeLeaveTypeDao{

	@Override
	public OfficeLeaveType setField(ResultSet rs) throws SQLException{
		OfficeLeaveType officeLeaveType = new OfficeLeaveType();
		officeLeaveType.setId(rs.getString("id"));
		officeLeaveType.setName(rs.getString("name"));
		officeLeaveType.setUnitId(rs.getString("unit_id"));
		officeLeaveType.setState(rs.getInt("state"));
		officeLeaveType.setIsDeleted(rs.getInt("is_deleted"));
		return officeLeaveType;
	}

	@Override
	public OfficeLeaveType save(OfficeLeaveType officeLeaveType){
		String sql = "insert into office_leave_type(id, name, unit_id, state, is_deleted) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeLeaveType.getId())){
			officeLeaveType.setId(createId());
		}
		Object[] args = new Object[]{
			officeLeaveType.getId(), officeLeaveType.getName(), 
			officeLeaveType.getUnitId(), officeLeaveType.getState(), 
			officeLeaveType.getIsDeleted()
		};
		update(sql, args);
		return officeLeaveType;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_leave_type set is_deleted = 1 where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeLeaveType officeLeaveType){
		String sql = "update office_leave_type set name = ? where id = ?";
		Object[] args = new Object[]{
			officeLeaveType.getName(), officeLeaveType.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLeaveType getOfficeLeaveTypeById(String id){
		String sql = "select * from office_leave_type where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLeaveType> getOfficeLeaveTypeMapByIds(String[] ids){
		String sql = "select * from office_leave_type where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypeList(){
		String sql = "select * from office_leave_type";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypePage(Pagination page){
		String sql = "select * from office_leave_type";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdList(String unitId, Integer state){
		String sql = "select * from office_leave_type where is_deleted = 0 and unit_id = ? and state = ? ";
		return query(sql, new Object[]{unitId, state}, new MultiRow());
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_leave_type where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public Map<String,OfficeLeaveType> getleaveTypeNameByLeaveIds(String[] ids) {
		String sql="select * from office_leave_type where ID in ";
		return queryForInSQL(sql, null,new Object[]{ids} , new MapRow());
	}
}