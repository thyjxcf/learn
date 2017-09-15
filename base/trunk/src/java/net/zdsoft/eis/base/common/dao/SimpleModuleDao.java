/**
 * 
 */
package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.SimpleModule;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: ModuleDao.java, v 1.0 2007-11-9 下午05:51:14 zhaosf Exp $
 */

public interface SimpleModuleDao {
	/**
	 * 取得指定父节点的module的列表
	 * 
	 * @param platform
	 *            所属平台
	 * @param subsystem
	 * @param parentId
	 * @return
	 */
	public List<SimpleModule> getModules(int platform, int subsystem,
			Long parentId);

	/**
	 * 根据id取模块信息
	 * 
	 * @param id
	 * @return
	 */
	public SimpleModule getModule(Long id);

	/**
	 * 取启用的子系统
	 * 
	 * @return
	 */
	public Set<Integer> getActiveSubsytems(int platform);

	public Map<Integer, SimpleModule> getModulesMap();

	public Map<Integer, SimpleModule> getModulesMap(Integer... intIds);
	
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
}
