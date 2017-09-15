/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.data.dao.RoleDao;

public class RoleDaoImpl extends BaseDao<Role> implements RoleDao {

    private static final String SQL_INSERT_ROLE = "INSERT INTO sys_role(id,identifier,unitid,"
            + "name,modid,operid,dynamicdataset,isactive,description,subsystem,"
            + "refid,roletype) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE_ROLE = "UPDATE sys_role SET identifier=?,unitid=?,"
            + "name=?,modid=?,operid=?,dynamicdataset=?,isactive=?,description=?,subsystem=?,"
            + "refid=?,roletype=? WHERE id=?";

    private static final String SQL_DELETE_ROLE_BY_IDS = "DELETE sys_role WHERE id IN";

    private static final String SQL_UPDATE_ROLE_BY_IDS = "UPDATE sys_role SET isactive = ? WHERE id IN ";

    private static final String SQL_FIND_ROLES_BY_IDS = "SELECT * FROM sys_role WHERE id IN";
    private static final String SQL_FIND_ROLE_BY_ID = "SELECT * FROM sys_role WHERE id=?";

    private static final String SQL_FIND_ROLE_BY_UNITID_IDENTIFIER = "SELECT * FROM sys_role WHERE unitid = ? AND identifier = ?";
    private static final String SQL_FIND_ROLE_BY_UNITID_ROLENAME = "SELECT * FROM sys_role WHERE unitid = ? AND name = ?";

    private static final String SQL_FIND_ROLE_BY_UNITID = "SELECT * FROM sys_role WHERE unitid = ?";
    private static final String SQL_FIND_ROLE_BY_UNITID_NOTINROLETYPE = "SELECT * FROM sys_role WHERE unitid = ? AND roletype <> ? order by id asc";

    private static final String SQL_FIND_ROLE_BY_USERID = "SELECT r.* FROM sys_role r,sys_user_role l "
            + "WHERE r.id=l.roleid AND l.userid = ?";

    public Role setField(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getString("id"));
        role.setIdentifier(rs.getString("identifier"));
        role.setUnitid(rs.getString("unitid"));
        role.setName(rs.getString("name"));
        role.setModid(rs.getString("modid"));
        role.setOperid(rs.getString("operid"));
        role.setDynamicdataset(rs.getString("dynamicdataset"));
        role.setIsactive(rs.getBoolean("isactive"));
        role.setDescription(rs.getString("description"));
        role.setSubsystem(rs.getString("subsystem"));
        role.setRefid(rs.getInt("refid"));
        role.setRoletype(rs.getInt("roletype"));
        return role;
    }

    public void insertRole(Role role) {
        role.setId(getGUID());
        update(SQL_INSERT_ROLE, new Object[] { role.getId(), role.getIdentifier(),
                role.getUnitid(), role.getName(), role.getModid(), role.getOperid(),
                role.getDynamicdataset(), role.getIsactive(), role.getDescription(),
                role.getSubsystem(), role.getRefid(), role.getRoletype() }, new int[] {
                Types.CHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.INTEGER });
    }

    public void updateRole(Role role) {
        update(SQL_UPDATE_ROLE, new Object[] { role.getIdentifier(), role.getUnitid(),
                role.getName(), role.getModid(), role.getOperid(), role.getDynamicdataset(),
                role.getIsactive(), role.getDescription(), role.getSubsystem(), role.getRefid(),
                role.getRoletype(), role.getId() }, new int[] { Types.VARCHAR, Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    public void deleteRole(String[] roleIds) {
        updateForInSQL(SQL_DELETE_ROLE_BY_IDS, null, roleIds);
    }

    public void updateActive(String roleIds, boolean activateStatus) {
        String sql = SQL_UPDATE_ROLE_BY_IDS;
        sql += roleIds;
        update(sql, new Object[] { activateStatus });
    }

    public void saveRoles(List<Role> roles) {
        List<Object[]> args = new ArrayList<Object[]>();

        for (Role role : roles) {
            role.setId(null == role.getId() ? getGUID() : role.getId());
            args.add(new Object[] { role.getId(), role.getIdentifier(), role.getUnitid(),
                    role.getName(), role.getModid(), role.getOperid(), role.getDynamicdataset(),
                    role.getIsactive(), role.getDescription(), role.getSubsystem(),
                    role.getRefid(), role.getRoletype() });
        }

        int[] argTypes = { Types.CHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.INTEGER };

        batchUpdate(SQL_INSERT_ROLE, args, argTypes);
    }

    public Role getRole(String roleId) {
        return query(SQL_FIND_ROLE_BY_ID, new Object[] { roleId }, new SingleRow());
    }

    public Role getRole(String unitId, String identifier) {
        return query(SQL_FIND_ROLE_BY_UNITID_IDENTIFIER, new Object[] { unitId, identifier },
                new SingleRow());
    }

    public Role getRoleByName(String unitId, String roleName) {
        return query(SQL_FIND_ROLE_BY_UNITID_ROLENAME, new Object[] { unitId, roleName },
                new SingleRow());
    }

    public List<Role> getRoles(String[] roleIds) {
        return queryForInSQL(SQL_FIND_ROLES_BY_IDS, null, roleIds, new MultiRow());
    }

    public List<Role> getRoles(String unitId) {
        return query(SQL_FIND_ROLE_BY_UNITID, new Object[] { unitId }, new MultiRow());
    }

    public List<Role> getRolesByNotThisType(String unitId, int notInRoleType) {
        return query(SQL_FIND_ROLE_BY_UNITID_NOTINROLETYPE, new Object[] { unitId,
                new Integer(notInRoleType) }, new MultiRow());
    }

    public List<Role> getRolesByUserId(String userId) {
        return query(SQL_FIND_ROLE_BY_USERID, new Object[] { userId }, new MultiRow());
    }

}
