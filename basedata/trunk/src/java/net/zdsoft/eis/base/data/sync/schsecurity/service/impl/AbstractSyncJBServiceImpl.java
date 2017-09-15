package net.zdsoft.eis.base.data.sync.schsecurity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.SyncEntity;
import net.zdsoft.eis.base.data.sync.schsecurity.exception.SyncException;
import net.zdsoft.eis.base.data.sync.schsecurity.service.SyncJBService;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.keel.util.DateUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class AbstractSyncJBServiceImpl<T extends SyncEntity> implements SyncJBService{
	
	protected static final Logger LOG = LoggerFactory.getLogger("AbstractSyncJBServiceImpl");
	
	private final static String init32Id = "00000000000000000000000000000000";
	
	private final static String init6Id = "000000";
	
	private SystemIniService systemIniService;
	
	private ModuleService moduleService;
	
	abstract String getTimeCode();
	
	abstract String getLastGxsj();
	
	abstract void saveDatas(Date beginTime);

	abstract void insert(T entity);
	
	abstract void update(T entity);
	
	abstract List<T> getData();
	
	abstract void updateData(T entity);
	
	
	protected Date getLastUpdateTime(String timeMcode) {
		String dateString = systemIniService.getValue(timeMcode);
		if(StringUtils.isNotBlank(dateString)) {
			return DateUtils.string2Date(dateString, "yyyyMMddHHmmss");
		}
		return null;
	}
	
	protected void updateLastUpdateTime(String time) {
		if(StringUtils.isNotBlank(time)) {
			systemIniService.updateNowValue(getTimeCode(), time);
		}
	}
	
	@Override
	public void syncData() {
		//获取上次同步时间
		Date beginTime = getLastUpdateTime(getTimeCode());
		saveDatas(beginTime);
		//更新上次同步时间
		updateLastUpdateTime(getLastGxsj());
	}
	
	@Override
	public void handleData() {
		List<T> dataList = getData();
		for(T entity : dataList) {
			try{
				if(entity.SJBHLX.equals(JBSyncConstant.SJBHLX_INSERT)) {
					insert(entity);
				}else if(entity.SJBHLX.equals(JBSyncConstant.SJBHLX_UPDATE)) {
					update(entity);
				}else {
					throw new SyncException("没有符合变化数据数据类型["+entity.SJBHLX+"]的处理策略");
				}
				entity.setSyncResult(JBSyncConstant.SYNC_RESULT_SUCCESS);
			}catch(SyncException e) {
				entity.setSyncResult(JBSyncConstant.SYNC_RESULT_FAIL);
				entity.setException(e.getMessage());
			}catch (Exception e) {
				entity.setSyncResult(JBSyncConstant.SYNC_RESULT_FAIL);
				entity.setException(StringUtils.substring(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)), 0, 250));
				e.printStackTrace();
			}finally{
				entity.setModifyTime(new Date());
				updateData(entity);
			}
		}
	}
	
	public List<String> getModuleIdsBySubsystemid(String[] ids, int unitClass) {
		List<String> returnModules = new ArrayList<String>();
		for(String id : ids) {
			String key = JBSyncConstant.SUBSYSTEM_MODULE_CLASS + "_" + id + "_" + unitClass;
			List<String> moduleids = new ArrayList<String>();
			moduleids = RedisUtils.lrange(key);
			if(moduleids == null || moduleids.size() == 0) {
				List<Module> moduleList = moduleService.getModules(Integer.parseInt(id), unitClass);
				for(Module module : moduleList) {
					moduleids.add(module.getId()+"");
					RedisUtils.lpush(key, module.getId()+"");
				}
				RedisUtils.expire(key, 600);
			}
			returnModules.addAll(moduleids);
		}
		return returnModules;
	}
	
	public static String get32Id(String id) {
		return init32Id.substring(0,32-id.length()) + id;
	}
	
	public static String get6Id(String id) {
		return init6Id.substring(0,6-id.length()) + id;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
}
