package net.zdsoft.eis.base.common.dao;

import net.zdsoft.eis.base.common.entity.SchoolBuildingArea;

/**
 * 校舍面积 
 * @author 
 * 
 */
public interface SchoolBuildingAreaDao {
	/**
	 * 新增校舍面积
	 * @param schoolBuildingArea
	 * @return"
	 */
	public SchoolBuildingArea save(SchoolBuildingArea schoolBuildingArea);
	
	/**
	 * 根据ids数组删除校舍面积
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新校舍面积
	 * @param schoolBuildingArea
	 * @return
	 */
	public Integer update(SchoolBuildingArea schoolBuildingArea);
	
	/**
	 * 根据id获取校舍面积;
	 * @param id);
	 * @return
	 */
	public SchoolBuildingArea getSchoolBuildingAreaById(String id);
}
