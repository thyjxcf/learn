package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.base.common.dao.SystemPatchDao;
import net.zdsoft.eis.base.common.entity.SystemPatch;
import net.zdsoft.eis.frame.client.BaseDao;

public class SystemPatchDaoImpl extends BaseDao<SystemPatch> implements SystemPatchDao {
    private static final String SQL_FIND_PATCH_BY_SUBSYSTEM = "SELECT * FROM sys_patch WHERE subsystem=? ORDER BY createtime DESC";

    public SystemPatch setField(ResultSet rs) throws SQLException {
        SystemPatch systemPatch = new SystemPatch();
        systemPatch.setId(rs.getLong("id"));
        systemPatch.setPatchname(rs.getString("patchname"));
        systemPatch.setPatchversion(rs.getString("patchversion"));
        systemPatch.setDescription(rs.getString("description"));
        systemPatch.setCompatible(rs.getString("compatible"));
        systemPatch.setCreatetime(rs.getTimestamp("createtime"));
        systemPatch.setProversion(rs.getString("proversion"));
        systemPatch.setMainversion(rs.getInt("mainversion"));
        systemPatch.setType(rs.getInt("type"));
        systemPatch.setProject(rs.getString("project"));
        systemPatch.setSubsystem(rs.getInt("subsystem"));
        return systemPatch;
    }

    public SystemPatch getPathBySubSystem(Integer subSystem) {
        return query(SQL_FIND_PATCH_BY_SUBSYSTEM, new Object[] { subSystem }, new SingleRow());
    }

}
