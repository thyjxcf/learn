package net.zdsoft.eis.base.common.dao;

import net.zdsoft.eis.base.common.entity.SchoolBuilding;

/**
 * 校舍 
 * @author 
 * 
 */
public interface SchoolBuildingDao {
	/**
	 * 新增校舍
	 * @param schoolBuilding
	 * @return"
	 */
	public SchoolBuilding save(SchoolBuilding schoolBuilding);
	
	/**
	 * 根据ids数组删除校舍
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新校舍
	 * @param schoolBuilding
	 * @return
	 */
	public Integer update(SchoolBuilding schoolBuilding);
	
	/**
	 * 根据id获取校舍;
	 * @param id);
	 * @return
	 */
	public SchoolBuilding getSchoolBuildingById(String id);
}
