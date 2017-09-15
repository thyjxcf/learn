package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.keel.util.Pagination;

public interface BaseModuleDao {
	/**
	 * 根据unitClass和subSystemId(都可为null)分页查找
	 *
	 *@author "yangk"
	 * Jul 13, 2010 10:54:39 AM
	 * @param unitClass （可以为null）
	 * @param subSystemId（可以为null）
	 * @param page 
	 * @return
	 */
	public List<Module> takeModulesByPage(String unitClass, String subSystemId,
			Pagination page);

	/**
	 * 得到父级模块
	 *
	 *@author "yangk"
	 * Jul 13, 2010 1:48:14 PM
	 * @param subSystemId
	 * @param unitClass 
	 * @return
	 */
	public List<Module> takeParentModules(String subSystemId, String unitClass);

	/**
	 * 根据id将mark值置为0
	 *
	 *@author "yangk"
	 * Jul 13, 2010 2:20:55 PM
	 * @param ids
	 */
	public void updateCloseMark(Long[] ids);
	
	/**
     * 将不在In中的id的mark值置为0
     * 
     * @param ids
     */
    public void updateCloseMarkNotIn(Long[] ids);
    
	/**
	 * 更新模块，注意这个方法只会更新几个关键字： parentid orderid name subsystem limit mark 
	 *
	 *@author "yangk"
	 * Jul 16, 2010 3:25:38 PM
	 * @param m
	 */
	public void updateModule(Module m); 

}
