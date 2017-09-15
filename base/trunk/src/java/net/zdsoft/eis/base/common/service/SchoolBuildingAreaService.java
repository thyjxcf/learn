package net.zdsoft.eis.base.common.service;import net.zdsoft.eis.base.common.entity.SchoolBuildingArea;/** * 校舍面积  * @author  *  */public interface SchoolBuildingAreaService {	/**	 * 新增校舍面积	 * @param schoolBuildingArea	 * @return	 */	public SchoolBuildingArea save(SchoolBuildingArea schoolBuildingArea);		/**	 * 根据ids数组删除校舍面积数据	 * @param ids	 * @return	 */	public Integer delete(String[] ids);		/**	 * 更新校舍面积	 * @param schoolBuildingArea	 * @return	 */	public Integer update(SchoolBuildingArea schoolBuildingArea);		/**	 * 根据id获取校舍面积	 * @param id	 * @return	 */	public SchoolBuildingArea getSchoolBuildingAreaById(String id);	}