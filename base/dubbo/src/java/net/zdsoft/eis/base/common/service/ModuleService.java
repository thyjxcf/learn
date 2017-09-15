package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.leadin.cache.CacheManager;

/**
 * @author zhangza
 * @version $Revision: 1.17 $, $Date: 2007/03/21 08:36:44 $
 */
public interface ModuleService extends CacheManager, BasicModuleService {

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
	 * 根据数字型id，检索模块信息
	 * 
	 * @param intId
	 *            模块的数字型id
	 * @return 模块信息
	 */
	public Module getModuleByIntId(long intId);

	/**
	 * 通过mod编号获取mod
	 * 
	 * @param mid
	 * @return
	 */
	public Module getModuleForPB(String mid);

	/**
	 * 通过module编号获取module
	 * 
	 * @param modId
	 * @param unitClass
	 * @return
	 */
	public Module getModule(String mid, Integer unitClass);

	/**
	 * 返回模块Set属于的子系统号
	 * 
	 * @param modIds
	 * @param unitClass
	 * 
	 * @return
	 */
	public Set<Integer> getSubSystemSet(Set<Long> modIds, int unitClass);

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
	 * 手机端模块
	 * 
	 * @param subsystemId
	 * @param unitClass
	 * @param parentId
	 * @return
	 */
	public List<Module> getModulesForPC(int subsystemId, int unitClass,
			int unitType, Long parentId);
	
	/**
	 * 移动端模块
	 * @param parentId
	 * @param unitClass
	 * @param parm
	 * @return
	 */
	public List<Module> getModulesForMobile(Long parentId, int unitClass,
			int unitType, String parm);
	
	/**
	 * 取得指定父节点的module的列表，元素位Module对象 parentId=-1表示顶级module。
	 * 
	 * @param subsystemId
	 * @param unitClass
	 * @param parentId
	 * @return
	 */
	public List<Module> getModules(int subsystemId, int unitClass,
			int unitType, Long parentId);

	public List<Module> getModules(Integer... intIds);

	public Map<Integer, Module> getModulesMap(Integer... intIds);

	/**
	 * 取得所有可用的模块
	 * 
	 * @return
	 */
	public List<Module> getEnabledModules();
	
	public List<Module> getEnabledModules(long subsystemId);

	/**
	 * 获取当前单位所有启用的模块
	 * 
	 * @param unitClass
	 * @param unitType
	 * @return
	 */
	public List<Module> getEnabledModules(int unitClass, int unitType);

	/**
	 * 根据当前登录用户取有权限的子系统
	 * 
	 * @param unitClass
	 * @param unitType
	 * 
	 * @return
	 */
	public List<SubSystem> getSubSystems(int unitClass, int unitType);

	/**
	 * 获取所有公共模块主键id
	 * 
	 * @param unitClass
	 * @return
	 */
	Set<Long> getCommonModuleIdSet(int unitClass);

	/**
	 * 获取所有公共模块id
	 * 
	 * @param unitClass
	 * @return
	 */
	Set<Module> getCommonModelIdSet(int unitClass);

	/**
	 * 根据模块主url取模块信息
	 * 
	 * @param unitClass
	 * @param url
	 * @return
	 */
	public List<Module> getModulesByUrl(int unitClass, String url);

	/**
	 * 
	 * 取启用模块的子系统
	 * 
	 * @return
	 */
	public Map<String, String> getCacheSubsytemMap();

	/**
	 * 
	 * 取启用模块的子系统
	 * 
	 * @return
	 */
	public Set<Integer> getCacheSubsytem();

	/**
	 * 取得所有模块的整型ID
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

}
