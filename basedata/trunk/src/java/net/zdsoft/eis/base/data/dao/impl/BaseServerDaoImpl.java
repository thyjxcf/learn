/* 
 * @(#)BaseServerDaoImpl.java    Created on Dec 20, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.data.dao.BaseServerDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 20, 2010 3:42:30 PM $
 */
public class BaseServerDaoImpl extends BaseDao<Server> implements BaseServerDao {

    private static final String SQL_INSERT_SERVER = "INSERT INTO base_server(id,name,status,"
            + "code,base_sync_type,interface_type,capabilityurl,introduceurl,index_url,link_phone,"
            + "link_man,appoint,server_key,protocol,domain,port,server_code,server_type_id,is_passport,event_source,is_deleted,context) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_SERVER_BY_IDS = "UPDATE base_server SET is_deleted = 1,event_source=? WHERE id IN ";

    private static final String SQL_UPDATE_SERVER = "UPDATE base_server SET name=?,status=?,"
            + "code=?,base_sync_type=?,interface_type=?,capabilityurl=?,introduceurl=?,index_url=?,link_phone=?,"
            + "link_man=?,appoint=?,server_key=?,protocol=?,domain=?,port=?,server_code=?,"
            + "id=?,is_passport=?,event_source=?,context=? WHERE server_type_id=?";

    private static final String SQL_FIND_SERVERS_BY_STATUS = "SELECT * FROM base_server WHERE status=? AND is_deleted=0";

    @Override
    public Server setField(ResultSet rs) throws SQLException {
        Server server = new Server();
        server.setId(String.valueOf(rs.getInt("id")));
        server.setName(rs.getString("name"));
        server.setStatus(rs.getInt("status"));
        server.setCode(rs.getString("code"));
        server.setSeSyncType(rs.getInt("base_sync_type"));
        server.setInterfaceType(rs.getInt("interface_type"));
        server.setCapabilityurl(rs.getString("capabilityurl"));
        server.setIntroduceurl(rs.getString("introduceurl"));
        server.setIndexUrl(rs.getString("index_url"));
        server.setLinkPhone(rs.getString("link_phone"));
        server.setLinkMan(rs.getString("link_man"));
        server.setAppoint(rs.getString("appoint"));
        server.setServerKey(rs.getString("server_key"));
        server.setProtocol(rs.getString("protocol"));
        server.setDomain(rs.getString("domain"));
        server.setPort(rs.getInt("port"));
        server.setServerCode(rs.getString("server_code"));
        server.setServerTypeId(rs.getInt("server_type_id"));
        server.setPassport(rs.getBoolean("is_passport"));
        server.setContext(rs.getString("context"));
        return server;
    }

    public void insertServer(Server server) {
        server.setCreationTime(new Date());
        server.setModifyTime(new Date());
        server.setIsdeleted(false);
        update(SQL_INSERT_SERVER, new Object[] { Integer.valueOf(server.getId()), server.getName(),
                server.getStatus(), server.getCode(), server.getSeSyncType(),
                server.getInterfaceType(), server.getCapabilityurl(), server.getIntroduceurl(),
                server.getIndexUrl(), server.getLinkPhone(), server.getLinkMan(),
                server.getAppoint(), server.getServerKey(), server.getProtocol(),
                server.getDomain(), server.getPort(), server.getServerCode(),
                server.getServerTypeId(), server.isPassport() ? 1 : 0,
                server.getEventSourceValue(), server.getIsdeleted() ? 1 : 0,server.getContext() }, new int[] {
                Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER,
                Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                Types.INTEGER,Types.VARCHAR });
    }

    public void deleteServer(String[] serverIds, EventSourceType eventSource) {
        updateForInSQL(SQL_DELETE_SERVER_BY_IDS, new Object[] { eventSource.getValue() }, serverIds);
    }

    public void updateServer(Server server) {
        update(SQL_UPDATE_SERVER, new Object[] { server.getName(), server.getStatus(),
                server.getCode(), server.getSeSyncType(), server.getInterfaceType(),
                server.getCapabilityurl(), server.getIntroduceurl(), server.getIndexUrl(),
                server.getLinkPhone(), server.getLinkMan(), server.getAppoint(),
                server.getServerKey(), server.getProtocol(), server.getDomain(), server.getPort(),
                server.getServerCode(), Integer.valueOf(server.getId()),
                server.isPassport() ? 1 : 0, server.getEventSourceValue(),server.getContext(),
                Long.valueOf(server.getServerTypeId()) }, new int[] { Types.VARCHAR, Types.INTEGER,
                Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER,
                Types.INTEGER, Types.INTEGER, Types.VARCHAR,Types.BIGINT });
    }

    public List<Server> getServers(int status) {
        return query(SQL_FIND_SERVERS_BY_STATUS, new Object[] { Integer.valueOf(status) },
                new MultiRow());
    }
}
