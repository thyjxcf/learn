package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.ServerDao;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;

public class ServerDaoImpl extends BaseDao<Server> implements ServerDao {

	private static final String SQL_FIND_SERVER = "SELECT * FROM base_server WHERE is_deleted=0";
	private static final String SQL_FIND_SERVER_BY_ID = "SELECT * FROM base_server WHERE id=?";
	private static final String SQL_FIND_SERVERS_BY_IDS = "SELECT * FROM base_server WHERE is_deleted=0 and id IN ";
	private static final String SQL_FIND_SERVER_BY_SERVERCODE = "SELECT * FROM base_server WHERE server_code=? AND is_deleted=0";

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
		//System.out.println("=========="+rs.getString("second_domain") );
		server.setSecondDomain(rs.getString("second_domain"));
		server.setPort(rs.getInt("port"));
		server.setServerCode(rs.getString("server_code"));
		server.setServerTypeId(rs.getInt("server_type_id"));
		server.setPassport(rs.getBoolean("is_passport"));
		server.setContext(rs.getString("context"));
		return server;
	}

	public Server getServer(String serverId) {
		return query(SQL_FIND_SERVER_BY_ID, serverId, new SingleRow());
	}

	public Server getServerByServerCode(String serverCode) {
		return query(SQL_FIND_SERVER_BY_SERVERCODE, serverCode, new SingleRow());

	}

	public List<Server> getServers(Long[] serverIds) {
		return queryForInSQL(SQL_FIND_SERVERS_BY_IDS, null, serverIds,
				new MultiRow());
	}

	public Map<Long, String> getServerCodeMap() {
		return queryForMap(SQL_FIND_SERVER, new Object[] {},
				new MapRowMapper<Long, String>() {

					public Long mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getLong("id");
					}

					public String mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("server_code");
					}
				});
	}

	public Map<Long, String> getServerTypeMap() {
		return queryForMap(SQL_FIND_SERVER, new Object[] {},
				new MapRowMapper<Long, String>() {

					public Long mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getLong("server_type_id");
					}

					public String mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("server_code");
					}
				});
	}

	public Map<String, Long> getServerMapByServerCode() {
		return queryForMap(SQL_FIND_SERVER, new Object[] {},
				new MapRowMapper<String, Long>() {

					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("server_code");
					}

					public Long mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getLong("id");
					}
				});
	}

	public Map<Long, Server> getServerMap() {
		return queryForMap(SQL_FIND_SERVER, new MapRowMapper<Long, Server>() {

			@Override
			public Long mapRowKey(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("id");
			}

			@Override
			public Server mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
	
	public Map<String, Server> getServerMapByCode() {
		return queryForMap(SQL_FIND_SERVER, new MapRowMapper<String, Server>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("code");
			}

			@Override
			public Server mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
}
