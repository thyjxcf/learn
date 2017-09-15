package net.zdsoft.eis.system.frame.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.system.frame.entity.UserApp;

public interface UserAppDao {
	/**
	 * 新增用户应用
	 * @param userApp
	 */
	public void addUserApp(UserApp userApp);
	
	/**
	 * 批量新增应用
	 * @param appList
	 */
	public void addUserApps(List<UserApp> appList);
	
	/**
	 * 根据用户id取用户应用List
	 * @param userId
	 * @param subsystem
	 * @return
	 */
	public List<UserApp> getUserAppListByUserId(String userId, Integer subsystem);
	
	/**
	 * 根据用户id取用户应用的模块id
	 */
	public List<String> getUserAppModuleIdsByUserId(String userId, Integer subsystem);
	
	/**
	 * 根据用户id取用户应用map
	 * @param userId
	 * @param subsystem
	 * @return
	 */
	public Map<Integer,UserApp> getserAppMapByUserId(String userId, Integer subsystem);
	
	/***
	 * 删除
	 * @param id
	 */
	public void deleteUserApp(String id);
	
	/***
	 * 删除
	 * @param id
	 */
	public void deleteUserAppByUserId(String userId);
}
