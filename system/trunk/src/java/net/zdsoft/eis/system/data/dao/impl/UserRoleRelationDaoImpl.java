package net.zdsoft.eis.system.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.data.dao.UserRoleRelationDao;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;

/**
 * @author luxingmu
 * @version $Revision: 1.7 $, $Date: 2007/01/09 10:04:42 $
 */
public class UserRoleRelationDaoImpl extends BaseDao<UserRoleRelation> implements
        UserRoleRelationDao {
    private static final String SQL_INSERT_USERROLERELATION = "INSERT INTO sys_user_role(id,userid,roleid,"
            + "flag) " + "VALUES(?,?,?,?)";

    private static final String SQL_DELETE_USERROLERELATION_BY_ROLEID = "DELETE sys_user_role WHERE roleid IN ";

    private static final String SQL_DELETE_USERROLERELATION_BY_USERID = "DELETE sys_user_role WHERE userid IN ";
    
    private static final String SQL_DELETE_USERROLERELATION_BY_ID = "DELETE sys_user_role WHERE id IN ";
    
    private static final String SQL_UPDATE_USERROLERELATION_BY_USERID = "UPDATE sys_user_role SET "
            + "flag=0 WHERE userid=?";

    private static final String SQL_UPDATE_USERROLERELATION_BY_USERID_ROLEID = "UPDATE sys_user_role SET "
            + "flag=1 WHERE userid=? AND roleid=?";

    private static final String SQL_FIND_USERROLERELATION_BY_ROLEID = "SELECT * FROM sys_user_role WHERE roleid=?";
    
    private static final String SQL_FIND_USERROLERELATION_BY_ROLEID_AND_DEPTID = "SELECT sur.* FROM sys_user_role sur, base_user bu "
    		+ "WHERE sur.userid = bu.id and sur.roleid= ? and bu.dept_id = ?";
    
    private static final String SQL_FIND_USERROLERELATION_BY_ROLEIDS = "SELECT * FROM sys_user_role WHERE roleid in";

    private static final String SQL_FIND_USERROLERELATION_BY_USERID = "SELECT * FROM sys_user_role WHERE userid=? AND flag=0";

    private static final String SQL_FIND_USERROLERELATION_BY_ROLE_USERID = "SELECT r.* FROM sys_user_role r,sys_role l "
            + "WHERE l.isactive=1 and l.id=r.roleid and r.userid IN ";
    
    private static final String SQL_FIND_USERROLERELATION_ALL_BY_USERID = "SELECT * FROM sys_user_role WHERE flag=0 and userid in";
    
    public UserRoleRelation setField(ResultSet rs) throws SQLException {
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setId(rs.getString("id"));
        userRoleRelation.setUserid(rs.getString("userid"));
        userRoleRelation.setRoleid(rs.getString("roleid"));
        userRoleRelation.setFlag(rs.getInt("flag"));
        return userRoleRelation;
    }

    public void deleteUserRole(String roleIds, String[] userIds) {
        String sql = SQL_DELETE_USERROLERELATION_BY_ROLEID +  roleIds ;
        if (userIds == null) {
            update(sql);
        } else {
            sql += " AND userid IN ";
            updateForInSQL(sql, null, userIds);
        }
    }

    public void deleteUserRole(String[] userIds) {
        updateForInSQL(SQL_DELETE_USERROLERELATION_BY_USERID, null, userIds);
    }

    public void saveDefaultUserRole(String userId, String roleId) {
        update(SQL_UPDATE_USERROLERELATION_BY_USERID, new Object[] { userId });
        update(SQL_UPDATE_USERROLERELATION_BY_USERID_ROLEID, new Object[] { userId, roleId });
    }

    public void saveUserRoles(List<UserRoleRelation> userRoles) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();

        for (UserRoleRelation userRoleRelation : userRoles) {
            userRoleRelation.setId(getGUID());
            listOfArgs.add(new Object[] { userRoleRelation.getId(), userRoleRelation.getUserid(),
                    userRoleRelation.getRoleid(), userRoleRelation.getFlag() });
        }

        int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER };

        batchUpdate(SQL_INSERT_USERROLERELATION, listOfArgs, argTypes);
    }

    public List<UserRoleRelation> getUserRolesByRoleId(String roleId) {
        return query(SQL_FIND_USERROLERELATION_BY_ROLEID, new Object[] { roleId }, new MultiRow());
    }
    
    @Override
    public List<UserRoleRelation> getUserRolesByRoleIdAndDeptId(String roleId,
    		String deptId) {
    	return query(SQL_FIND_USERROLERELATION_BY_ROLEID_AND_DEPTID, new Object[] { roleId, deptId }, new MultiRow());
    }
    
    public List<UserRoleRelation> getUserRolesByRoleIds(String[] roleIds){
    	return queryForInSQL(SQL_FIND_USERROLERELATION_BY_ROLEIDS, null, roleIds, new MultiRow());
    }
    public String getDefaultRoleId(String userId) {
        List<UserRoleRelation> resultList = query(SQL_FIND_USERROLERELATION_BY_USERID,
                new Object[] { userId }, new MultiRow());
        if (!resultList.isEmpty()) {
            return ((UserRoleRelation) resultList.get(0)).getRoleid();
        }
        return null;
    }

    public List<UserRoleRelation> getUserRolesByUserIds(String[] userIds) {
        return queryForInSQL(SQL_FIND_USERROLERELATION_BY_ROLE_USERID, null, userIds,
                new MultiRow());
    }
    
    @Override
	public List<UserRoleRelation> getUserRolesByUserId(String userId) {
		List<UserRoleRelation> resultList = query(SQL_FIND_USERROLERELATION_BY_USERID,
                new Object[] { userId }, new MultiRow());
		return resultList;
	}

	@Override
	public void deleteUserRoles(String[] ids) {
		updateForInSQL(SQL_DELETE_USERROLERELATION_BY_ID,null,ids);
	}

	@Override
	public List<UserRoleRelation> getAllUserRoleRelaction(String[] userIds) {
		return queryForInSQL(SQL_FIND_USERROLERELATION_ALL_BY_USERID, null, userIds, new MultiRow()); 
	}
}
