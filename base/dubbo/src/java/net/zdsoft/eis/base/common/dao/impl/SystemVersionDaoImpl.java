package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.base.common.dao.SystemVersionDao;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.frame.client.BaseDao;

public class SystemVersionDaoImpl extends BaseDao<SystemVersion> implements SystemVersionDao {
    private static final String SQL_FIND_VERSION_BY_USING = "SELECT * FROM sys_version where isusing = '1'";
    private static final String SQL_UPDATE_VERSION = "Update sys_version Set isusing = '1' where productid = ?";

    public SystemVersion setField(ResultSet rs) throws SQLException {
        SystemVersion systemVersion = new SystemVersion();
        systemVersion.setId(rs.getLong("id"));
        systemVersion.setName(rs.getString("name"));
        systemVersion.setProductId(rs.getString("productid"));
        systemVersion.setDescription(rs.getString("description"));
        systemVersion.setCreatedate(rs.getTimestamp("createdate"));
        systemVersion.setCurversion(rs.getString("curversion"));
        systemVersion.setBuild(rs.getString("build"));
        systemVersion.setUsing(rs.getString("isusing"));
        return systemVersion;
    }

    public SystemVersion getSystemVersion() {
        return query(SQL_FIND_VERSION_BY_USING, new SingleRow());
    }

    public int updateProduct(String productId) {
        return update(SQL_UPDATE_VERSION, productId);
    }

}
