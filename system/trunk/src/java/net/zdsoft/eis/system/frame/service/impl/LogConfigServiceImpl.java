package net.zdsoft.eis.system.frame.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.system.frame.dao.LogConfigDao;
import net.zdsoft.eis.system.frame.entity.LogConfig;
import net.zdsoft.eis.system.frame.service.LogConfigService;

public class LogConfigServiceImpl implements LogConfigService {

	private LogConfigDao logConfigDao;
	private ModuleService moduleService;

	public void setLogConfigDao(LogConfigDao logConfigDao) {
		this.logConfigDao = logConfigDao;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void saveLogConfigs(LogConfig[] logConfigs) {
		for (int i = 0; i < logConfigs.length; i++) {
			logConfigDao.saveLogConfigDays(logConfigs[i]);
		}
	}

	public List<LogConfig> getLogConfigs(int unitClass, int unitType) {
		List<LogConfig> list = logConfigDao.getLogConfigs();

		List<SubSystem> subsystemList = moduleService.getSubSystems(unitClass,
				unitType);
		Map<String, SubSystem> subsystemMap = new HashMap<String, SubSystem>();
		SubSystem subSystem;
		for (int i = 0; i < subsystemList.size(); i++) {
			subSystem = (SubSystem) subsystemList.get(i);
			subsystemMap.put(String.valueOf(subSystem.getId()), subSystem);
		}
		List<LogConfig> resultlist = new ArrayList<LogConfig>();
		LogConfig logConfigdto;
		Map<String, LogConfig> dtoMap = new HashMap<String, LogConfig>();
		for (int i = 0; i < list.size(); i++) {
			logConfigdto = (LogConfig) list.get(i);
			subSystem = (SubSystem) subsystemMap.get(String
					.valueOf(logConfigdto.getSubSystem()));
			if (subSystem != null) {
				logConfigdto.setSubSystemName(subSystem.getName());
			} else {
				continue;
			}
			resultlist.add(logConfigdto);
			dtoMap.put(String.valueOf(logConfigdto.getSubSystem()),
					logConfigdto);
		}

		// 如果对应的子系统在日志配置中没有，则初始化进去
		for (int i = 0; i < subsystemList.size(); i++) {
			subSystem = (SubSystem) subsystemList.get(i);
			LogConfig config = dtoMap.get(String.valueOf(subSystem.getId()));
			if (null == config) {
				config = new LogConfig();
				config.setDays(0);
				config.setFlag(1);
				config.setSubSystem(subSystem.getId().intValue());
				config.setSubSystemName(subSystem.getName());
				resultlist.add(config);
			}
		}
		return resultlist;
	}

}
