package net.zdsoft.eis.system.frame.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.system.frame.entity.UserApp;

public interface UserAppService {
	/**
	 * 新增用户应用
	 * 
	 * @param userApp
	 */
	public void addUserApp(UserApp userApp);

	public void addUserApps(String userId, int ownerType, String[] moduleIds);
	/**
	 * 不删除已有的应用，新增新的
	 */
	public void addToUserApps(String userId, int ownerType, String[] moduleIds);

	/**
	 * 根据用户id取用户应用list
	 * 
	 * @param userId
	 * @param subsystem
	 * @return
	 */
	public List<UserApp> getUserAppListByUserId(String userId, Integer subsystem);

	/**
	 * 根据用户id取用户应用map
	 * 
	 * @param userId
	 * @param subsystem
	 * @return
	 */
	public Map<Integer, UserApp> getserAppMapByUserId(String userId,
			Integer subsystem);

	/***
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteUserApp(String id);

	/**
	 * 获取用户所有的model（除去常用的）
	 * @param userId
	 * @param subsystemId
	 * @param ownerType
	 * @param unitClass
	 * @param modSets
	 * @return
	 */
	public List<Module> getModuleList(String userId, String subsystemId,
			int ownerType, int unitClass, Set<Integer> modSets,boolean hasDefault,boolean showAll);

	/**
	 * 获取用户常用的model
	 * @param userId
	 * @param ownerType
	 * @param modSets
	 * @return
	 */
	public List<UserApp> getUserAppList(String userId, int ownerType,
			Set<Integer> modSets,boolean hasDefault,boolean showAll,boolean isSecondUrl);
}
