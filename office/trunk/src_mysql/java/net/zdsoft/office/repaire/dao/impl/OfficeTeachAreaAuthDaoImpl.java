package net.zdsoft.office.repaire.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.dao.OfficeTeachAreaAuthDao;
import net.zdsoft.office.repaire.entity.OfficeTeachAreaAuth;

import org.apache.commons.lang.StringUtils;
/**
 * office_teach_area_auth
 * @author 
 * 
 */
public class OfficeTeachAreaAuthDaoImpl extends BaseDao<OfficeTeachAreaAuth> implements OfficeTeachAreaAuthDao{

	@Override
	public OfficeTeachAreaAuth setField(ResultSet rs) throws SQLException{
		OfficeTeachAreaAuth officeTeachAreaAuth = new OfficeTeachAreaAuth();
		officeTeachAreaAuth.setId(rs.getString("id"));
		officeTeachAreaAuth.setTeachAreaId(rs.getString("teach_area_id"));
		officeTeachAreaAuth.setUserId(rs.getString("user_id"));
		officeTeachAreaAuth.setState(rs.getString("state"));
		officeTeachAreaAuth.setCreateTime(rs.getTimestamp("create_time"));
		return officeTeachAreaAuth;
	}

	@Override
	public OfficeTeachAreaAuth save(OfficeTeachAreaAuth officeTeachAreaAuth){
		String sql = "insert into office_teach_area_auth(id, teach_area_id, user_id, state, create_time) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeTeachAreaAuth.getId())){
			officeTeachAreaAuth.setId(createId());
		}
		Object[] args = new Object[]{
			officeTeachAreaAuth.getId(), officeTeachAreaAuth.getTeachAreaId(), 
			officeTeachAreaAuth.getUserId(), officeTeachAreaAuth.getState(), 
			officeTeachAreaAuth.getCreateTime()
		};
		update(sql, args);
		return officeTeachAreaAuth;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_teach_area_auth where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeTeachAreaAuth officeTeachAreaAuth){
		String sql = "update office_teach_area_auth set teach_area_id = ?, user_id = ?, state = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeTeachAreaAuth.getTeachAreaId(), officeTeachAreaAuth.getUserId(), 
			officeTeachAreaAuth.getState(), officeTeachAreaAuth.getCreateTime(), 
			officeTeachAreaAuth.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeTeachAreaAuth getOfficeTeachAreaAuthById(String id){
		String sql = "select * from office_teach_area_auth where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTeachAreaAuth> getOfficeTeachAreaAuthMapByIds(String[] ids){
		String sql = "select * from office_teach_area_auth where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthList(){
		String sql = "select * from office_teach_area_auth";
		return query(sql, new MultiRow());
	}
	@Override
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthList(String state,
			String userId) {
		String sql = "select * from office_teach_area_auth where state = ? and user_id = ?";
		return query(sql, new Object[]{state,userId}, new MultiRow());
	}
	@Override
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthPage(Pagination page){
		String sql = "select * from office_teach_area_auth";
		return query(sql, new MultiRow(), page);
	}
	@Override
	public void deleteByUserIds(String[] userIds) {
		String sql = "delete from office_teach_area_auth where state = 1 and user_id in";
		updateForInSQL(sql, null, userIds);
	}
	@Override
	public void saveBach(List<OfficeTeachAreaAuth> arealist) {
		// TODO Auto-generated method stub
		String sql = "insert into office_teach_area_auth(id, teach_area_id, user_id, state, create_time) values(?,?,?,?,?)";
		List<Object[]> args = new ArrayList<Object[]>();
		for(OfficeTeachAreaAuth phi : arealist){
			if(phi == null){
				continue;
			}
			if (StringUtils.isBlank(phi.getId())){
				phi.setId(createId());
			}
			Object[] arg = new Object[]{
					phi.getId(),phi.getTeachAreaId(), phi.getUserId(), 
					phi.getState(),phi.getCreateTime()
				};
			args.add(arg);
		}
		int[] argsType = new int[]{
				Types.CHAR,Types.CHAR,Types.CHAR,
				Types.CHAR,Types.DATE,
			};
		batchUpdate(sql, args, argsType);
	}
}