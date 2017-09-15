package net.zdsoft.eis.base.data.sync.schsecurity.job;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.service.SyncJBService;
import net.zdsoft.eis.base.data.sync.schsecurity.service.SyncJBFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SyncJBJob implements Runnable{
	
	private static final Log log = LogFactory.getLog(SyncJBJob.class);
	
	private String types;
	private Boolean isRunning = false;
	private SystemIniService systemIniService;
	
	
	@Override
	public void run() {
		syncJob(types);
	}
	
	public void syncJob(String types) {
		if(systemIniService.getBooleanValue(JBSyncConstant.JB_SYNC_KG)) {
			if(!isRunning) {
				isRunning = true;
				try{
					if(types != null) {
						String[] typeArray = types.split(",");
						//获取原始数据
						for(String type : typeArray) {
							try{
								SyncJBService syncJB = SyncJBFactory.getSyncJB(type);
								if(syncJB != null) {
									log.error("开始同步["+SyncJBFactory.syncTypeNameMap.get(type)+"]数据");
									syncJB.syncData();
									log.error("结束同步["+SyncJBFactory.syncTypeNameMap.get(type)+"]数据");
								}else {
									throw new RuntimeException("找不到type:" + type + "的同步策略");
								}
							}catch(Exception e){
								log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
							}
						}
						
						//获取原始数据
						for(String type : typeArray) {
							try{
								SyncJBService syncJB = SyncJBFactory.getSyncJB(type);
								if(syncJB != null) {
									log.error("开始处理["+SyncJBFactory.syncTypeNameMap.get(type)+"]数据");
									syncJB.handleData();
									log.error("结束处理["+SyncJBFactory.syncTypeNameMap.get(type)+"]数据");
								}else {
									throw new RuntimeException("找不到type:" + type + "的处理策略");
								}
							}catch(Exception e){
								log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
							}
						}
					}else {
						log.error("同步类型不能为空");
					}
				}catch(Exception e){
					log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
				}finally{
					isRunning = false;
				}
			}
		}else {
			log.error("未启用同步同一用户平台基础数据");
		}
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
}
