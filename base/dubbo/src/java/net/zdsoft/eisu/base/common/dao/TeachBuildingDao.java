package net.zdsoft.eisu.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.TeachBuilding;

/**
 * 楼层信息表 
 * @author 
 * 
 */
public interface TeachBuildingDao {
	
	public TeachBuilding getBaseTeachBuildingById(String id);
		
	/**
	 * 根据ids数组查询楼层信息表map
	 * @param ids
	 * @return
	 */
	public Map<String, TeachBuilding> getBaseTeachBuildingMapByIds(String[] ids);

	/**
	 * 根据楼的属性 查询
	 * @param unitId
	 * @param areaId
	 * @param buildingType
	 * @return
	 */
	public List<TeachBuilding> getBaseTeachBuildingByBuildingType(
			String unitId, String areaId, String buildingType);
}
