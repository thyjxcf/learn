package net.zdsoft.eis.system.frame.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SimpleModule;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SimpleModuleService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.system.frame.dao.UserAppDao;
import net.zdsoft.eis.system.frame.entity.UserApp;
import net.zdsoft.eis.system.frame.serial.SerialManager;
import net.zdsoft.eis.system.frame.service.UserAppService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

public class UserAppServiceImpl implements UserAppService {

	private UserAppDao userAppDao;

	private ModuleService moduleService;

	private SubSystemService subSystemService;

	private SimpleModuleService simpleModuleService;// 模块

	private SerialManager serialManager;

	private ServerService serverService;

	@Override
	public void addUserApp(UserApp userApp) {
		userAppDao.addUserApp(userApp);
	}

	@Override
	public void addUserApps(String userId, int ownerType, String[] moduleIds) {
		userAppDao.deleteUserAppByUserId(userId);
		addUserAppsDirect(userId, ownerType, moduleIds);
	}

	/**
	 * 新增userApp
	 */
	private void addUserAppsDirect(String userId, int ownerType,
			String[] moduleIds) {
		if (moduleIds == null)
			return;
		Integer[] intIds = new Integer[moduleIds.length];
		for (int i = 0; i < moduleIds.length; i++) {
			intIds[i] = Integer.valueOf(moduleIds[i]);
		}
		List<UserApp> appList = new ArrayList<UserApp>();
		if (ownerType == User.TEACHER_LOGIN) {
			Map<Integer, Module> moduleMap = moduleService
					.getModulesMap(intIds);
			for (int i = 0; i < intIds.length; i++) {
				Module module = moduleMap.get(intIds[i]);
				if (module != null) {
					UserApp app = new UserApp();
					app.setModuleId(module.getId().intValue());
					app.setSubsystem(module.getSubsystem());
					app.setUserId(userId);
					app.setOrderNo(i);
					appList.add(app);
				}
			}

		} else {
			Map<Integer, SimpleModule> smMap = simpleModuleService
					.getModulesMap(intIds);
			for (int i = 0; i < intIds.length; i++) {
				SimpleModule module = smMap.get(intIds[i]);
				if (module != null) {
					UserApp app = new UserApp();
					app.setModuleId(module.getId().intValue());
					app.setSubsystem(module.getSubsystem());
					app.setUserId(userId);
					app.setOrderNo(i);
					appList.add(app);
				}
			}

		}
		userAppDao.addUserApps(appList);
	}

	public void addToUserApps(String userId, int ownerType, String[] moduleIds) {
		List<String> mids = userAppDao.getUserAppModuleIdsByUserId(userId, 0);
		if (CollectionUtils.isNotEmpty(mids)) {
			moduleIds = ArrayUtils.removeElements(moduleIds,
					mids.toArray(new String[0]));
		}
		addUserAppsDirect(userId, ownerType, moduleIds);
	}

	public List<Module> getModuleList(String userId, String subsystemId,
			int ownerType, int unitClass, Set<Integer> modSets,
			boolean hasDefault, boolean showAll) {
		List<Module> allModuleList = new ArrayList<Module>();
		Map<Integer, UserApp> appMap = null;
		Map<Integer, UserApp> defaultAppMap = null;
		List<Module> moduleList = new ArrayList<Module>();
		if (StringUtils.isBlank(subsystemId))
			subsystemId = "0";
		int subsystem = Integer.valueOf(subsystemId);

		Set<Integer> subsystemSets = new HashSet<Integer>();
		List<SubSystem> subsystemList = subSystemService.getSubSystems();
		for (SubSystem s : subsystemList) {
			subsystemSets.add(s.getId().intValue());
		}
		// 教师平台
		if (ownerType == User.TEACHER_LOGIN) {
			if (showAll) {
				if (subsystem != 0) {
					allModuleList = moduleService.getEnabledModules(subsystem);
				} else {
					allModuleList = moduleService.getEnabledModules();
				}
			} else {
				if (subsystem != 0) {
					allModuleList = moduleService.getModules(subsystem,
							unitClass);
				} else {
					allModuleList = moduleService.getModules(modSets
							.toArray(new Integer[0]));

				}
			}
			appMap = getserAppMapByUserId(userId, subsystem);
			if (hasDefault) {
				defaultAppMap = getserAppMapByUserId(BaseConstant.ZERO_GUID,
						subsystem);
				if (appMap == null) {
					appMap = new HashMap<Integer, UserApp>();
				}
				appMap.putAll(defaultAppMap);
			}
			for (Module module : allModuleList) {
				if ((modSets.contains(module.getId().intValue()) || showAll)
						&& !appMap.containsKey(module.getId().intValue())) {
					if (-1 != module.getParentid().intValue()
							&& StringUtils.isNotBlank(module.getUrl())
							&& module.getIsActive() == 1
							&& StringUtils.isBlank(module.getParm())
							&& subsystemSets.contains(module.getSubsystem()))
						moduleList.add(module);
				}
			}
		} else {// 学生平台 or 家长平台
			List<SimpleModule> topModules = simpleModuleService
					.getModules(
							ownerType == User.STUDENT_LOGIN ? BaseConstant.PLATFORM_STUPLATFORM
									: BaseConstant.PLATFORM_FAMPLATFORM,
							subsystem, Long.valueOf(-1));
			for (Iterator<SimpleModule> iter = topModules.iterator(); iter
					.hasNext();) {
				SimpleModule simpleModule = (SimpleModule) iter.next();
				List<SimpleModule> secondModelList = getFilterModules(
						ownerType == User.STUDENT_LOGIN ? BaseConstant.PLATFORM_STUPLATFORM
								: BaseConstant.PLATFORM_FAMPLATFORM, subsystem,
						simpleModule.getId());
				appMap = getserAppMapByUserId(userId, subsystem);
				if (hasDefault) {
					defaultAppMap = getserAppMapByUserId(
							BaseConstant.ZERO_GUID, subsystem);
					if (appMap != null && appMap.size() > 0) {
						appMap.putAll(defaultAppMap);
					}
				}
				for (SimpleModule m : secondModelList) {
					if (appMap.containsKey(m.getId().intValue())
							&& subsystemSets.contains(simpleModule
									.getSubsystem())) {
						continue;
					}
					Module module = new Module();
					module.setParentModName(simpleModule.getName());
					module.setId(m.getId());
					module.setSubsystem(m.getSubsystem());
					module.setName(m.getName());
					module.setPicture(m.getPicture());
					moduleList.add(module);
				}
			}
		}
		return moduleList;
	}

	@SuppressWarnings("rawtypes")
	public List<UserApp> getUserAppList(String userId, int ownerType,
			Set<Integer> modSets, boolean hasDefault, boolean showAll,
			boolean isSecondUrl) {
		List<UserApp> appList = new ArrayList<UserApp>();
		if (hasDefault) {
			List<UserApp> defaultAppList = getUserAppListByUserId(
					BaseConstant.ZERO_GUID, 0);
			appList.addAll(defaultAppList);
		}
		List<UserApp> tempAppList = getUserAppListByUserId(userId, 0);
		appList.addAll(tempAppList);
		List<UserApp> resultList = new ArrayList<UserApp>();
		if (CollectionUtils.isNotEmpty(appList)) {
			Integer[] moduleIds = new Integer[appList.size()];
			int count = 0;
			for (UserApp app : appList) {
				moduleIds[count++] = app.getModuleId();
			}
			Map<Integer, SubSystem> subsystemMap = subSystemService
					.getCacheSubSystemMap();
			Set<Integer> subsystemSet = serialManager.getSubsystems();
			Map moduleMap = null;
			if (ownerType == User.TEACHER_LOGIN) {
				moduleMap = moduleService.getModulesMap(moduleIds);
			} else {
				moduleMap = simpleModuleService.getModulesMap(moduleIds);
			}
			Map<String, Server> serverMap = serverService.getServerMapByCode();
			if (serverMap == null) {
				serverMap = new HashMap<String, Server>();
			}
			for (UserApp app : appList) {
				// 教师平台
				if (ownerType == User.TEACHER_LOGIN) {
					if (modSets.contains(app.getModuleId()) || showAll) {
						if (moduleMap.containsKey(app.getModuleId())) {
							Module mo = (Module) moduleMap.get(app
									.getModuleId());
							if (mo.getIsActive() == 1) {
								app.setName(mo.getName());
								app.setPicture(mo.getPicture());
								// 判断是否为老系统的模块，新老系统的跳转方式完全不一样
								if (StringUtils.isNotBlank(app.getPicture())
										&& app.getPicture().contains(".")) {
									if (subsystemMap.containsKey(app
											.getSubsystem())) {
										SubSystem subsystem = subsystemMap
												.get(app.getSubsystem());

										if (serverMap.containsKey(subsystem
												.getCode())) {
											Server server = serverMap
													.get(subsystem.getCode());
											String xurl = "";
											if (isSecondUrl)
												xurl = server.getSecondUrl()
														+ subsystem
																.getIndexUrl();
											else
												xurl = server.getUrl()
														+ server
																.getIndexUrl();

											if (StringUtils.isNotBlank(xurl) && xurl.contains("?")) {
												app.setUrl(xurl + "&moduleID="
														+ app.getModuleId());
											} else {
												app.setUrl(xurl);
											}
										} else {
											String xurl = subsystem
													.getIndexUrl();
											if (StringUtils.isNotBlank(xurl)) {
												if (xurl.contains("?")) {
													app.setUrl(xurl
															+ "&moduleID="
															+ app.getModuleId());
												} else {
													app.setUrl(xurl);
												}
											}
										}
									}
								} else {
									app.setUrl(mo.getUrl());
								}

								app.setParentId(mo.getParentid().intValue());
								app.setLimit(mo.getLimit());
								resultList.add(app);
							}
						}
					}
				} else {// 学生平台or家长平台
					if (subsystemSet.contains(app.getSubsystem())) {
						if (moduleMap.containsKey(app.getModuleId())) {
							SimpleModule mo = (SimpleModule) moduleMap.get(app
									.getModuleId());
							app.setName(mo.getName());
							app.setPicture(mo.getPicture());

							// 判断是否为老系统的模块，新老系统的跳转方式完全不一样
							if (StringUtils.isNotBlank(app.getPicture())
									&& app.getPicture().contains(".")) {
								if (subsystemMap
										.containsKey(app.getSubsystem())) {
									String indexUrl = subsystemMap.get(
											app.getSubsystem()).getIndexUrl();
									if (StringUtils.isNotBlank(indexUrl)) {
										if (indexUrl.contains("?")) {
											app.setUrl(indexUrl + "&moduleID="
													+ app.getModuleId());
										} else {
											app.setUrl(indexUrl);
										}
									}
								}
							} else {
								app.setUrl(mo.getUrl());
							}

							// app.setUrl(mo.getUrl());
							app.setParentId(mo.getParentid().intValue());
							app.setLimit(mo.getLimit());
							resultList.add(app);
						}
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 取得指定父节点的module的列表，过滤掉不上的子系统
	 * 
	 * @param subsystem
	 * @param parentId
	 * @return
	 */
	private List<SimpleModule> getFilterModules(int platform, int subsystem,
			Long parentId) {
		List<SimpleModule> modules = new ArrayList<SimpleModule>();
		List<SimpleModule> list = simpleModuleService.getModules(platform,
				subsystem, parentId);
		// 取启用了哪些子系统
		Set<Integer> subsystemSet = serialManager.getSubsystems();
		// 过滤掉没有上子系统的模块
		for (SimpleModule m : list) {
			String dss = m.getDataSubsystems();
			boolean find = true;
			if (StringUtils.isNotBlank(dss)) {
				String[] subsystemIds = m.getDataSubsystems().split(",");
				for (String subsystemId : subsystemIds) {
					if (!(subsystemSet.contains(Integer.valueOf(subsystemId
							.trim())))) {
						find = false;
						break;
					}
				}
			}
			if (find) {
				modules.add(m);
			}
		}
		return modules;
	}

	@Override
	public void deleteUserApp(String id) {
		userAppDao.deleteUserApp(id);
	}

	@Override
	public List<UserApp> getUserAppListByUserId(String userId, Integer subsystem) {
		return userAppDao.getUserAppListByUserId(userId, subsystem);
	}

	public Map<Integer, UserApp> getserAppMapByUserId(String userId,
			Integer subsystem) {
		return userAppDao.getserAppMapByUserId(userId, subsystem);
	}

	public void setUserAppDao(UserAppDao userAppDao) {
		this.userAppDao = userAppDao;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setSimpleModuleService(SimpleModuleService simpleModuleService) {
		this.simpleModuleService = simpleModuleService;
	}

	public void setSerialManager(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

}
