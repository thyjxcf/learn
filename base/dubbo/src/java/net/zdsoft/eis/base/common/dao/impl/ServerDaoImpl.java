package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.zdsoft.basedata.remote.service.ServerRemoteService;
import net.zdsoft.eis.base.common.dao.ServerDao;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.util.EntityUtils;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;

public class ServerDaoImpl extends BaseDao<Server> implements ServerDao {
	
	@Autowired
	private ServerRemoteService serverRemoteService;

//	private static final String SQL_FIND_SERVER = "SELECT * FROM base_server WHERE is_deleted=0";
//	private static final String SQL_FIND_SERVER_BY_ID = "SELECT * FROM base_server WHERE id=?";
//	private static final String SQL_FIND_SERVERS_BY_IDS = "SELECT * FROM base_server WHERE is_deleted=0 and id IN ";
//	private static final String SQL_FIND_SERVER_BY_SERVERCODE = "SELECT * FROM base_server WHERE server_code=? AND is_deleted=0";

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
		Server teacher = Server.dc(serverRemoteService.findById(serverId));
		return teacher;
		
	//	return query(SQL_FIND_SERVER_BY_ID, serverId, new SingleRow());
	}

	public Server getServerByServerCode(String serverCode) {
		Server teacher = Server.dc(serverRemoteService.findByServerCode(serverCode));
		return teacher;
		
	//	return query(SQL_FIND_SERVER_BY_SERVERCODE, serverCode, new SingleRow());

	}

	public List<Server> getServers(Long[] serverIds) {
		 String[] ids=new String[serverIds.length];
		 for (int i=0;i<serverIds.length;i++)
		 {
			 ids[i]=String.valueOf(serverIds[i]);//把long类型转化成string
		 }
		
		List<Server> servers = Server.dt(serverRemoteService.findByIds(ids));
		List<Server> servers2=new ArrayList<Server>();
 		for (Server server : servers) {
 			
			if(!server.getIsdeleted()){
				servers.add(server);
				continue;
			}
			
		}
		
		return servers2;
		
		
//		return queryForInSQL(SQL_FIND_SERVERS_BY_IDS, null, serverIds,
//				new MultiRow());
	}

	public Map<Long, String> getServerCodeMap() {
		List<Server> servers = Server.dt(serverRemoteService.findAll());
		List<Server> servers2=new ArrayList<Server>();
 		for (Server server : servers) {
 			
			if(!server.getIsdeleted()){
				servers.add(server);
				continue;
			}
			
		}
 		
 		Long[] LongIds= getLongIds(servers2);
 		Map<Long, String> map=new HashMap<Long, String>();
 		for (int i = 0; i < LongIds.length; i++) {
			map.put(LongIds[i],servers2.get(i).getServerCode() );
		}
 		
 		
 		return map;
		
		
//		return queryForMap(SQL_FIND_SERVER, new Object[] {},
//				new MapRowMapper<Long, String>() {
//
//					public Long mapRowKey(ResultSet rs, int rowNum)
//							throws SQLException {
//						return rs.getLong("id");
//					}
//
//					public String mapRowValue(ResultSet rs, int rowNum)
//							throws SQLException {
//						return rs.getString("server_code");
//					}
//				});
	}

	public Map<Long, String> getServerTypeMap() {
		List<Server> servers = Server.dt(serverRemoteService.findAll());
		List<Server> servers2=new ArrayList<Server>();
 		for (Server server : servers) {
 			
			if(!server.getIsdeleted()){
				servers.add(server);
				continue;
			}
			
		}	
 		return EntityUtils.getMap(servers2, "serverTypeId", "serverCode");
		
		
		
//		return queryForMap(SQL_FIND_SERVER, new Object[] {},
//				new MapRowMapper<Long, String>() {
//
//					public Long mapRowKey(ResultSet rs, int rowNum)
//							throws SQLException {
//						return rs.getLong("server_type_id");
//					}
//
//					public String mapRowValue(ResultSet rs, int rowNum)
//							throws SQLException {
//						return rs.getString("server_code");
//					}
//				});
	}

	public Map<String, Long> getServerMapByServerCode() {
		List<Server> servers = Server.dt(serverRemoteService.findAll());
		List<Server> servers2=new ArrayList<Server>();
 		for (Server server : servers) {
 			
			if(!server.getIsdeleted()){
				servers.add(server);
				continue;
			}
			
		}
 		
 		Long[] LongIds= getLongIds(servers2);
 		Map<String, Long> map=new HashMap<String, Long>();
 		for (int i = 0; i < LongIds.length; i++) {
			map.put(servers2.get(i).getServerCode(),LongIds[i] );
		}
 		
 		
 		return map;
		
		
//		List<Server> servers = Server.dt(serverRemoteService.findAll());
//		List<Server> servers2=new ArrayList<Server>();
// 		for (Server server : servers) {
// 			
//			if(!server.getIsdeleted()){
//				servers.add(server);
//				continue;
//			}
//			
//		}
// 		return EntityUtils.getMap(servers2, "serverCode", "id");
		
//		return queryForMap(SQL_FIND_SERVER, new Object[] {},
//				new MapRowMapper<String, Long>() {
//
//					public String mapRowKey(ResultSet rs, int rowNum)
//							throws SQLException {
//						return rs.getString("server_code");
//					}
//
//					public Long mapRowValue(ResultSet rs, int rowNum)
//							throws SQLException {
//						return rs.getLong("id");
//					}
//				});
	}

	public Map<Long, Server> getServerMap() {
		List<Server> servers = Server.dt(serverRemoteService.findAll());
		List<Server> servers2=new ArrayList<Server>();
 		for (Server server : servers) {
 			
			if(!server.getIsdeleted()){
				servers.add(server);
				continue;
			}
			
		}
 		
 		Long[] LongIds= getLongIds(servers2);
 		Map<Long, Server> map=new HashMap<Long, Server>();
 		for (int i = 0; i < LongIds.length; i++) {
 			map.put(LongIds[i],servers2.get(i) );
		}
 		
 		
 		return map;
		
		
//		return queryForMap(SQL_FIND_SERVER, new MapRowMapper<Long, Server>() {
//
//			@Override
//			public Long mapRowKey(ResultSet rs, int rowNum) throws SQLException {
//				return rs.getLong("id");
//			}
//
//			@Override
//			public Server mapRowValue(ResultSet rs, int rowNum)
//					throws SQLException {
//				return setField(rs);
//			}
//		});
	}
	
	public Map<String, Server> getServerMapByCode() {
		List<Server> servers = Server.dt(serverRemoteService.findAll());
		List<Server> servers2=new ArrayList<Server>();
 		for (Server server : servers) {
 			
			if(!server.getIsdeleted()){
				servers.add(server);
				continue;
			}
			
		}	
 		return EntityUtils.getMap(servers2, "code");
		
		
//		return queryForMap(SQL_FIND_SERVER, new MapRowMapper<String, Server>() {
//
//			@Override
//			public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
//				return rs.getString("code");
//			}
//
//			@Override
//			public Server mapRowValue(ResultSet rs, int rowNum)
//					throws SQLException {
//				return setField(rs);
//			}
//		});
	}
	//TODO  String转化成Long
	private Long[] getLongIds(List<Server> servers2){
		
		
 		String[] idStrings=new String[servers2.size()];
 		for (int i = 0; i < servers2.size(); i++) {
			idStrings[i]=servers2.get(i).getId();
		}
 		Long[] LongIds= (Long[])ConvertUtils.convert(idStrings, long.class);
 		
 		return LongIds;
 		
		
		
	}
}
