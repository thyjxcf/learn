package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import net.zdsoft.eis.base.common.entity.ServerAuthorize;
import net.zdsoft.eis.base.data.dao.BaseServerAuthorizeDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.leadin.util.AssembleTool;

public class BaseServerAuthorizeDaoImpl extends BaseDao<ServerAuthorize> implements
        BaseServerAuthorizeDao {

    private static final String SQL_INSERT_AUTH = "INSERT INTO base_server_authorize(id,server_id,user_id,"
            + "creation_time,event_source,is_deleted) VALUES(?,?,?,?,?,0)";
    private static final String SQL_DELETE_AUTH_BY_USERID = "update base_server_authorize set is_deleted=1 WHERE user_id in";

    private static final String SQL_DELETE_AUTH_BY_SERVERID = "update base_server_authorize set is_deleted=1 WHERE server_id in";

    private static final String SQL_FIND_AUTH_BY_USERID = "SELECT * FROM base_server_authorize WHERE is_deleted=0 AND user_id in";
    private static final String SQL_FIND_AUTH_BY_SERVERID = "SELECT * FROM base_server_authorize WHERE is_deleted=0 AND server_id=?";

    @Override
    public ServerAuthorize setField(ResultSet rs) throws SQLException {
        ServerAuthorize auth = new ServerAuthorize();
        auth.setId(rs.getString("id"));
        auth.setServerId(rs.getLong("server_id"));
        auth.setUserId(rs.getString("user_id"));
        return auth;
    }

    public void deleteServerAuthorizes(String[] userIds) {
        updateForInSQL(SQL_DELETE_AUTH_BY_USERID, null, userIds);
    }

    public void deleteServerAuthorizes(Long[] serverIds, String[] userIds) {
        if(ArrayUtils.isEmpty(serverIds)){
        	return;
        }
    	String sql = SQL_DELETE_AUTH_BY_SERVERID + "("
                + AssembleTool.getAssembledString(serverIds, ",") + ")";
        if (userIds == null) {
            update(sql);
        } else {
            sql += " AND user_id IN ";
            updateForInSQL(sql, null, userIds);
        }
    }

    public void addServerAuthorizes(List<ServerAuthorize> authList) {
        List<Object[]> list = new ArrayList<Object[]>(authList.size());
        for (ServerAuthorize dto : authList) {
            // dto.setCreationTime(new java.sql.Date());
            Object[] rs = new Object[] { getGUID(), dto.getServerId().intValue(), dto.getUserId(),
                    new java.util.Date(), dto.getEventSourceValue() };
            list.add(rs);
        }
        batchUpdate(SQL_INSERT_AUTH, list, new int[] { Types.CHAR, Types.INTEGER, Types.CHAR,
                Types.TIMESTAMP, Types.INTEGER });

    }

    public List<ServerAuthorize> getServerAuthorizes(String[] userIds) {
        return queryForInSQL(SQL_FIND_AUTH_BY_USERID, null, userIds, new MultiRow());
    }

    public List<ServerAuthorize> getServerAuthorizes(Long serverId) {
        return query(SQL_FIND_AUTH_BY_SERVERID, new Object[] { serverId }, new MultiRow());
    }

}
