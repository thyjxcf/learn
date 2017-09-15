/**
 * 
 */
package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.SimpleModule;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: ModuleService.java, v 1.0 2007-11-9 下午05:47:06 zhaosf Exp $
 */

public interface SimpleModuleService extends BasicModuleService {
    /**
     * 取得指定父节点的module的列表
     * @param platform 
     * @param subsystem
     * @param parentId
     * 
     * @return
     */
    public List<SimpleModule> getModules(int platform, int subsystem, Long parentId);

    /**
     * 取模块的所有上级
     * 
     * @param moduleId
     * @return
     */
    public List<SimpleModule> getParentModules(Long moduleId);
    
    /**
     * 取启用的子系统
     * 
     * @return
     */
    public Set<Integer> getActiveSubsytems(int platform);
    
    /**
	 * 获取所有的模块map
	 * @return
	 */
	public Map<Integer, SimpleModule> getModulesMap();
	
    /**
     * 根据id获取模块
     */
    public SimpleModule getModule(long id);
    
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
