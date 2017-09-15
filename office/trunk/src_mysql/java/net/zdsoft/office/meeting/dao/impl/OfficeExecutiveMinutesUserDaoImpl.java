package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveMinutesUserDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMinutesUser;

import org.apache.commons.lang.StringUtils;
/**
 * office_executive_minutes_user
 * @author 
 * 
 */
public class OfficeExecutiveMinutesUserDaoImpl extends BaseDao<OfficeExecutiveMinutesUser> implements OfficeExecutiveMinutesUserDao{

	@Override
	public OfficeExecutiveMinutesUser setField(ResultSet rs) throws SQLException{
		OfficeExecutiveMinutesUser officeExecutiveMinutesUser = new OfficeExecutiveMinutesUser();
		officeExecutiveMinutesUser.setId(rs.getString("id"));
		officeExecutiveMinutesUser.setUnitId(rs.getString("unit_id"));
		officeExecutiveMinutesUser.setUserId(rs.getString("user_id"));
		return officeExecutiveMinutesUser;
	}

	@Override
	public OfficeExecutiveMinutesUser save(OfficeExecutiveMinutesUser officeExecutiveMinutesUser){
		String sql = "insert into office_executive_minutes_user(id, unit_id, user_id) values(?,?,?)";
		if (StringUtils.isBlank(officeExecutiveMinutesUser.getId())){
			officeExecutiveMinutesUser.setId(createId());
		}
		Object[] args = new Object[]{
			officeExecutiveMinutesUser.getId(), officeExecutiveMinutesUser.getUnitId(), 
			officeExecutiveMinutesUser.getUserId()
		};
		update(sql, args);
		return officeExecutiveMinutesUser;
	}
	
	@Override
	public void batchSave(List<OfficeExecutiveMinutesUser> list) {
		String sql = "insert into office_executive_minutes_user(id, unit_id, user_id) values(?,?,?)";
		
		List<Object[]> objs = new ArrayList<Object[]>();
		for(OfficeExecutiveMinutesUser minutesUser:list){
			if (StringUtils.isBlank(minutesUser.getId())){
				minutesUser.setId(createId());
			}
			Object[] args = new Object[]{
				minutesUser.getId(), minutesUser.getUnitId(), 
				minutesUser.getUserId()
			};
			objs.add(args);
		}
		batchUpdate(sql, objs, new int[] {Types.CHAR, Types.CHAR, Types.CHAR});
		
	}
	
	@Override
	public void deleteByUnitId(String unitId) {
		String sql = "delete from office_executive_minutes_user where unit_id = ? ";
		update(sql, new Object[]{unitId});
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_executive_minutes_user where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExecutiveMinutesUser officeExecutiveMinutesUser){
		String sql = "update office_executive_minutes_user set unit_id = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveMinutesUser.getUnitId(), officeExecutiveMinutesUser.getUserId(), 
			officeExecutiveMinutesUser.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExecutiveMinutesUser getOfficeExecutiveMinutesUserById(String id){
		String sql = "select * from office_executive_minutes_user where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserMapByIds(String[] ids){
		String sql = "select * from office_executive_minutes_user where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserList(){
		String sql = "select * from office_executive_minutes_user";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserPage(Pagination page){
		String sql = "select * from office_executive_minutes_user";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserByUnitIdList(String unitId){
		String sql = "select * from office_executive_minutes_user where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_executive_minutes_user where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public boolean isMinutesUser(String unitId, String userId) {
		String sql = "select count(1) from office_executive_minutes_user where unit_id = ? and user_id = ? ";
		int i = queryForInt(sql, new Object[]{unitId,userId});
		if(i>0){
			return true;
		}
		return false;
	}
}