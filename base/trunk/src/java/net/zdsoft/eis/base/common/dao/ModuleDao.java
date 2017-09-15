package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Module;

public interface ModuleDao {

	/**
	 * 开启所有模块
	 */
	public void enableModules();

	/**
	 * 根据子系统屏蔽模块
	 * 
	 * @param subSystemId
	 */
	public void disableModules(int subSystemId);

	/**
	 * 根据子系统开启模块
	 * 
	 * @param subSystemId
	 */
	public void enableModules(int subSystemId);

	/**
	 * 通过id获取module
	 * 
	 * @param intId
	 * @return
	 */
	public Module getModule(int intId);

	/**
	 * 获取指定moduleId的模块
	 * 
	 * @param moduleId
	 *            模块id，32位的GUID
	 * @return 模块信息
	 */
	public Module getModuleByModuleId(String moduleId);

	/**
	 * 通过module id编号获取mod
	 * 
	 * @param modId
	 * @return
	 */
	public Module getModuleForPB(String modId);

	/**
	 * 通过module编号获取module
	 * 
	 * @param modId
	 * @param unitClass
	 * @return
	 */
	public Module getModule(String modId, Integer unitClass);

	/**
	 * 获取指定moduleId的模块
	 * 
	 * @param unitClass
	 * @param url
	 *            模块url
	 * @return 模块信息
	 */
	public List<Module> getModulesByUrl(int unitClass, String url);

	/**
	 * 通过module编号获取module
	 * 
	 * @param modIds
	 * @param unitClass
	 * @return
	 */
	public List<Module> getModules(String[] modIds, Integer unitClass);

	/**
	 * 根据单位类型及子系统号获取该子系统的所有模块
	 * 
	 * @param subsystemId
	 * @param unitClass
	 * @return
	 */
	public List<Module> getModules(int subsystemId, int unitClass);

	/**
	 * 取得指定父节点的module的列表，包括适用于各个单位类型的模块 parentId=-1表示顶级module。
	 * 
	 * @param subsystemId
	 * @param unitClass
	 *            单位分类
	 * @param parentId
	 * @return List<Module>
	 */
	public List<Module> getModules(int subsystemId, int unitClass, Long parentId);

	/**
	 * 取得指定父节点的module的列表，只包含适用于指定单位类型的模块 parentId=-1表示顶级module。
	 * 
	 * @param subsystemId
	 * @param unitClass
	 *            单位分类
	 * @param parentId
	 * @param unitType
	 *            单位类型
	 * @return List<Module>
	 */
	public List<Module> getModules(int subsystemId, int unitClass,
			Long parentId, int unitType);
	
	/**
	 * pc
	 * 
	 * @param subsystemId
	 * @param unitClass
	 *            单位分类
	 * @param parentId
	 * @param unitType
	 *            单位类型
	 * @return List<Module>
	 */
	public List<Module> getModulesForPc(int subsystemId, int unitClass,
			Long parentId, int unitType);
	
	/**
	 * mobile
	 * @param parentId
	 * @param unitClass
	 *            单位分类
	 * @param unitType
	 *            单位类型
	 * @param parm
	 * @return List<Module>
	 */
	public List<Module> getModulesForMobile(Long parentId, int unitClass,
			int unitType, String parm);

	/**
	 * 取得所有模块
	 * 
	 * @return
	 */
	public List<Integer> getAllModuleIds();

	/**
	 * 取得所有模块Map
	 * 
	 * @return
	 */
	public Map<Integer, Module> getAllModuleMap();

	/**
	 * 获取所有启用的模块
	 * 
	 * @param unitClass
	 * @return
	 */
	public List<Module> getEnabledModules();

	/**
	 * 根据子系统的数字型ID检索可用的模块信息
	 * 
	 * @param subsystemId
	 *            子系统的数字型ID
	 * @return 模块列表
	 */
	public List<Module> getEnabledModules(long subsystemId);

	public List<Module> getModules(Integer... intIds);

	public Map<Integer, Module> getModulesMap(Integer... intIds);

	/**
	 * 获取当前单位所有启用的模块
	 * 
	 * @param unitClass
	 * @return
	 */
	public List<Module> getEnabledModulesByUnitClass(int unitClass);

	/**
	 * 获取当前单位所有启用的模块
	 * 
	 * @param unitClass
	 * @param unitType
	 * @return
	 */
	public List<Module> getEnabledModules(int unitClass, int unitType);

	/**
	 * 取当前启用的子系统
	 * 
	 * @param unitClass
	 *            单位分类
	 * @param unitType
	 *            单位类型
	 * @return
	 */
	public List<Integer> getEnabledSubsytems(int unitClass, int unitType);

}
