package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.dao.CustomRoleDao;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.frame.client.BaseDao;

public class CustomRoleDaoImpl extends BaseDao<CustomRole> implements
		CustomRoleDao {

	private static final String SQL_INSERT_CUSTOM_ROLE = "insert into sys_custom_role(id,unit_id,role_name,role_code,subsystems,order_id,type,remark) values(?,?,?,?,?,?,?,?)";

	private static final String SQL_FIND_CUSTOM_ROLE_LIST_BY_UNITID = "select * from sys_custom_role where unit_id=? order by order_id";
	
	private static final String SQL_FIND_CUSTOM_ROLE_LIST_BY_UNITIDS = "select * from sys_custom_role where unit_id in";

	private static final String SQL_FIND_CUSTOM_ROLE_BY_ROLECODE = "select * from sys_custom_role where unit_id=? and role_code=?";

	private static final String SQL_FIND_CUSTOM_ROLE_BY_ID = "select * from sys_custom_role where id=?";

	private static final String SQL_DELETE_CUSTOM_ROLE_BY_UNIT_ID = "delete from sys_custom_role where unit_id=?";
	
	private static final String SQL_FIND_CUSTOM_ROLE_IN_UNITID_BY_ROLECODE = "select * from sys_custom_role where role_code=? and unit_id in";

	@Override
	public CustomRole setField(ResultSet rs) throws SQLException {
		CustomRole role = new CustomRole();
		role.setId(rs.getString("id"));
		role.setUnitId(rs.getString("unit_id"));
		role.setRoleName(rs.getString("role_name"));
		role.setRoleCode(rs.getString("role_code"));
		role.setSubsystems(rs.getString("subsystems"));
		role.setOrderId(rs.getInt("order_id"));
		role.setType(rs.getInt("type"));
		role.setRemark(rs.getString("remark"));
		return role;
	}

	public void addCustomRoles(List<CustomRole> roleList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < roleList.size(); i++) {
			CustomRole r = roleList.get(i);
			r.setId(getGUID());
			Object[] objs = new Object[] { r.getId(), r.getUnitId(),
					r.getRoleName(), r.getRoleCode(), r.getSubsystems(),
					r.getOrderId(), r.getType(), r.getRemark() };
			listOfArgs.add(objs);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
				Types.VARCHAR };
		batchUpdate(SQL_INSERT_CUSTOM_ROLE, listOfArgs, argTypes);
	}

	@Override
	public void deleteCustomRoles(String unitId) {
		update(SQL_DELETE_CUSTOM_ROLE_BY_UNIT_ID, new Object[] { unitId });
	}

	@Override
	public List<CustomRole> getCustomRoleList(String unitId) {
		return query(SQL_FIND_CUSTOM_ROLE_LIST_BY_UNITID,
				new Object[] { unitId }, new MultiRow());
	}
	
	@Override
	public List<CustomRole> getCustomRoleList(String[] unitIds) {
		return queryForInSQL(SQL_FIND_CUSTOM_ROLE_LIST_BY_UNITIDS, null, unitIds, new MultiRow()," order by order_id");
	}

	@Override
	public CustomRole getCustomRoleByCode(String unitId, String roleCode) {
		return query(SQL_FIND_CUSTOM_ROLE_BY_ROLECODE, new Object[] { unitId,
				roleCode }, new SingleRow());
	}

	@Override
	public CustomRole getCustomRoleById(String id) {
		return query(SQL_FIND_CUSTOM_ROLE_BY_ID, new Object[] { id },
				new SingleRow());
	}

	@Override
	public List<CustomRole> getCustomRoleList(String[] unitIds,
			String roleCode) {
		return queryForInSQL(SQL_FIND_CUSTOM_ROLE_IN_UNITID_BY_ROLECODE, new Object[]{roleCode}, unitIds, new MultiRow());
	}

}
