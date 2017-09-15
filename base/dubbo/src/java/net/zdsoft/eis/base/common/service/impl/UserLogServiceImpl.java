package net.zdsoft.eis.base.common.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.UserLogDao;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.UserLog;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UserLogService;
import net.zdsoft.keel.util.Pagination;

public class UserLogServiceImpl implements UserLogService {
	private UserLogDao userLogDao;
	private ModuleService moduleService;

	public void setUserLogDao(UserLogDao userLogDao) {
		this.userLogDao = userLogDao;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void insertUserLog(UserLog userLogDto) {
		userLogDao.insertUserLog(userLogDto);
	}

	public void deleteUserLogs(String[] ids) {
		userLogDao.deleteUserLogs(ids);
	}

	public void deleteUserLogs(Integer subSystem, Integer date) {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, -date);
		userLogDao.deleteUserLogs(subSystem, ca.getTime());
	}

	/**
	 * 对于dtoList设置模块名称
	 * 
	 * @param list
	 * @return
	 */
	private List<UserLog> setModName(List<UserLog> list, Integer unitClass) {
		UserLog userLog;
		Set<String> set = new HashSet<String>();
		String[] modIds;
		for (int i = 0; i < list.size(); i++) {
			userLog = (UserLog) list.get(i);
			modIds = userLog.getModId().split(",");
			for (int j = 0; j < modIds.length; j++) {
				set.add(modIds[j]);
			}
		}
		List<Module> mlist = moduleService.getModules((String[]) set
				.toArray(new String[] {}), unitClass);
		Module module;
		Map<String, Module> map = new HashMap<String, Module>();
		for (int i = 0; i < mlist.size(); i++) {
			module = (Module) mlist.get(i);
			map.put(module.getMid(), module);
		}

		StringBuffer modName;
		for (int i = 0; i < list.size(); i++) {
			userLog = (UserLog) list.get(i);
			if (userLog.getModId() == null) {
				continue;
			}
			modName = new StringBuffer();
			modIds = userLog.getModId().split(",");
			for (int j = 0; j < modIds.length; j++) {
				module = (Module) map.get(modIds[j]);
				if (module != null) {
					modName.append(module.getName());
					if (j != modIds.length - 1) {
						modName.append("、");
					}
				}
			}
			userLog.setModName(modName.toString());
		}
		return list;
	}

	public List<UserLog> getUserLogs(Date beginTime, Date endTime,
			Integer subSystem, String unitid, Integer unitClass, Pagination page) {
		List<UserLog> list = userLogDao.getUserLogs(beginTime, endTime,
				subSystem, unitid,page);
		return setModName(list, unitClass);
	}

	public Integer getLogCount() {
		return userLogDao.getLogCount();
	}

}
