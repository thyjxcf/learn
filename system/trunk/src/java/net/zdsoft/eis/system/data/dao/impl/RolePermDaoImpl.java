package net.zdsoft.eis.system.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.data.dao.RolePermDao;
import net.zdsoft.eis.system.data.entity.RolePerm;
import net.zdsoft.keel.dao.MultiRowMapper;

/**
 * @author luxingmu
 * @version $Revision: 1.7 $, $Date: 2007/01/09 10:04:42 $
 */
public class RolePermDaoImpl extends BaseDao<RolePerm> implements RolePermDao {

    private static final String SQL_INSERT_ROLEPERM = "INSERT INTO sys_role_perm(id,roleid,moduleid,"
            + "operids) " + "VALUES(?,?,?,?)";

    private static final String SQL_DELETE_ROLEPERM_BY_ROLEIDS = "DELETE sys_role_perm WHERE roleid IN ";
    
    private static final String SQL_UPDATE_ROLEPERM = "UPDATE sys_role_perm set roleid=?,moduleid=?,operids=? where id=?";
    
    private static final String SQL_DELETE_ROLEPERM_BY_IDS = "DELETE sys_role_perm WHERE id IN ";
    private static final String SQL_FIND_ROLEPERM_BY_ID = "SELECT * FROM sys_role_perm WHERE id=?";

    private static final String SQL_FIND_ROLEPERM_BY_ROLEIDS = "SELECT * FROM sys_role_perm WHERE roleid IN ";

    private static final String SQL_FIND_ROLEPERM_WITH_MODULE_ID ="select distinct roleid from sys_role_perm where moduleid in(select id from sys_model where mid=?) and roleid in";
    
    public RolePerm setField(ResultSet rs) throws SQLException {
        RolePerm rolePerm = new RolePerm();
        rolePerm.setId(rs.getString("id"));
        rolePerm.setRoleid(rs.getString("roleid"));
        rolePerm.setModuleid(rs.getLong("moduleid"));
        rolePerm.setOperids(rs.getString("operids"));
        return rolePerm;
    }

    public void saveRolePerms(List<RolePerm> rolePerms) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (RolePerm rolePerm : rolePerms) {
            rolePerm.setId(getGUID());
            listOfArgs.add(new Object[] { rolePerm.getId(), rolePerm.getRoleid(),
                    rolePerm.getModuleid(), rolePerm.getOperids() });
        }
        int[] argTypes = { Types.CHAR, Types.CHAR, Types.BIGINT, Types.VARCHAR };
        batchUpdate(SQL_INSERT_ROLEPERM, listOfArgs, argTypes);
    }

    public void deleteRolePerm(String... roleIds) {
        updateForInSQL(SQL_DELETE_ROLEPERM_BY_ROLEIDS, null, roleIds);
    }

    public RolePerm getRolePerm(String rolePermId) {
        return query(SQL_FIND_ROLEPERM_BY_ID, new Object[] { rolePermId }, new SingleRow());
    }

    public List<RolePerm> getRolePerms(String... roleIds) {
        return queryForInSQL(SQL_FIND_ROLEPERM_BY_ROLEIDS, null, roleIds, new MultiRow());
    }

	@Override
	public void deleteRolePermsByIds(String... Ids) {
		updateForInSQL(SQL_DELETE_ROLEPERM_BY_IDS, null, Ids);
	}

	@Override
	public void updateRolePerms(List<RolePerm> rolePerms) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for(RolePerm rolePerm:rolePerms){
			listOfArgs.add(new Object[]{rolePerm.getRoleid(),rolePerm.getModuleid(),rolePerm.getOperids(),rolePerm.getId()});
		}
		int[] argTypes = {  Types.CHAR, Types.BIGINT, Types.VARCHAR,Types.CHAR };
		batchUpdate(SQL_UPDATE_ROLEPERM, listOfArgs, argTypes);
	}

	/* (non-Javadoc)
	 * @see net.zdsoft.eis.system.data.dao.RolePermDao#getRolePerm(java.lang.String[], java.lang.String)
	 */
	@Override
	public List<String> getRolePerm(String[] roleIds, String moduleId) {
		return queryForInSQL(SQL_FIND_ROLEPERM_WITH_MODULE_ID, new Object[]{moduleId}, roleIds, new MultiRowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("roleid");
			}
		});
	}
}
