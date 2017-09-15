/* 
 * @(#)ServerService.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Server;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 4:20:10 PM $
 */
public interface ServerService {
	public Server getServer(String serverId);

	public Server getServerByServerCode(String serverCode);

	public List<Server> getServers(Long[] serverIds);

	/**
	 * 取server map
	 * 
	 * @return key=id,value=server_code
	 */
	public Map<Long, String> getServerCodeMap();

	/**
	 * 取server map
	 * 
	 * @return key=server_code,value=id
	 */
	public Map<String, Long> getServerMapByServerCode();

	/**
	 * 取server map
	 * 
	 * @return key=server_type_id,value=server_code
	 */
	public Map<Long, String> getServerTypeMap();

	public Map<Long, Server> getServerMap();

	public Map<String, Server> getServerMapByCode();
}
