package net.zdsoft.office.dutyinformation.dao;

import java.util.*;

import net.zdsoft.office.dutyinformation.entity.OfficePatrolPlace;
import net.zdsoft.keel.util.Pagination;
/**
 * office_patrol_place
 * @author 
 * 
 */
public interface OfficePatrolPlaceDao{

	/**
	 * 新增office_patrol_place
	 * @param officePatrolPlace
	 * @return
	 */
	public OfficePatrolPlace save(OfficePatrolPlace officePatrolPlace);

	/**
	 * 根据ids数组删除office_patrol_place
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_patrol_place
	 * @param officePatrolPlace
	 * @return
	 */
	public Integer update(OfficePatrolPlace officePatrolPlace);

	/**
	 * 根据id获取office_patrol_place
	 * @param id
	 * @return
	 */
	public OfficePatrolPlace getOfficePatrolPlaceById(String id);

	/**
	 * 根据ids数组查询office_patrol_placemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficePatrolPlace> getOfficePatrolPlaceMapByIds(String[] ids);

	/**
	 * 获取office_patrol_place列表
	 * @return
	 */
	public List<OfficePatrolPlace> getOfficePatrolPlaceList();

	/**
	 * 分页获取office_patrol_place列表
	 * @param page
	 * @return
	 */
	public List<OfficePatrolPlace> getOfficePatrolPlacePage(Pagination page);

	/**
	 * 根据unitId获取office_patrol_place列表
	 * @param unitId
	 * @return
	 */
	public List<OfficePatrolPlace> getOfficePatrolPlaceByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_patrol_place获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficePatrolPlace> getOfficePatrolPlaceByUnitIdPage(String unitId, Pagination page);
	
	public Map<String,OfficePatrolPlace> getOfficePMap(String[] ids);
}