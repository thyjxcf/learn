package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.keel.util.Pagination;

public interface BaseModuleService extends ModuleService {
	/**
	 * 取得模块的信息
	 *
	 *@author "yangk"
	 * Jul 12, 2010 8:43:09 PM
	 * @param unitClass	单位类型（可以为null）
	 * @param subSystemId	子系统id（可以为null）
	 * @param page
	 * @return
	 */
	public List<Module> findAllModules(String unitClass, String subSystemId,
			Pagination page);

	/**
	 * 得到父级模块list
	 *
	 *@author "yangk"
	 * Jul 13, 2010 5:07:34 PM
	 * @param subSysId 子系统id（可以为null）
	 * @param unitclass 
	 * @return
	 */
	public List<Module> findParentModules(String subSysId, String unitclass);

	/**
	 * 更新模块并刷新缓存，注意这个方法只会更新几个关键字：
	 * parentid  orderid name subsystem limit  mark 
	 *
	 *@author "yangk"
	 * Jul 16, 2010 3:14:00 PM
	 */
	public void updateModule(Module m);
	
	/**
     * 更新模块是否在用，定时或序列号更新时调用
     */
    public void updateModuleMark();
}
