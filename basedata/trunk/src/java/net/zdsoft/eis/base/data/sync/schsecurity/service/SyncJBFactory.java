package net.zdsoft.eis.base.data.sync.schsecurity.service;

import java.util.HashMap;
import java.util.Map;

import net.zdsoft.eis.base.data.sync.service.impl.SchoolSyncServiceImpl;
import net.zdsoft.eis.base.data.sync.service.impl.UserSyncServiceImpl;
import net.zdsoft.keelcnet.config.ContainerManager;

public class SyncJBFactory {
	
	/** 同步类型-机构*/
	public static final String SYNC_TYPE_JG = "1";
	
	/** 同步类型-学校 */
	public static final String SYNC_TYPE_XX = "2";
	
	/** 同步类型-用户*/
	public static final String SYNC_TYPE_YH = "3";
	
	/** 同步对应的类型名*/
	public static final Map<String, String> syncTypeNameMap;
	static{
		syncTypeNameMap = new HashMap<String, String>();
		syncTypeNameMap.put(SYNC_TYPE_JG, "机构");
		syncTypeNameMap.put(SYNC_TYPE_XX, "学校");
		syncTypeNameMap.put(SYNC_TYPE_YH, "用户");
	}
	
	public static SyncJBService getSyncJB(String type) {
		if(SYNC_TYPE_JG.equals(type)) {
			return (SyncJBService) ContainerManager.getComponent("jGJBXXSyncService");
		}else if(SYNC_TYPE_XX.equals(type)) {
			return (SyncJBService) ContainerManager.getComponent("xXJBXXBSyncService");
		}else if(SYNC_TYPE_YH.equals(type)) {
			return (SyncJBService) ContainerManager.getComponent("tYSFYHBSyncService");
		}
		return null;	
	}
}
