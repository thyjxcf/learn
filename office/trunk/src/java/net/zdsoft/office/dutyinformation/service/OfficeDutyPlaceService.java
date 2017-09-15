package net.zdsoft.office.dutyinformation.service;

import java.util.*;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyPlace;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_place
 * @author 
 * 
 */
public interface OfficeDutyPlaceService{

	/**
	 * 新增office_duty_place
	 * @param officeDutyPlace
	 * @return
	 */
	public OfficeDutyPlace save(OfficeDutyPlace officeDutyPlace);

	/**
	 * 根据ids数组删除office_duty_place数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void batchUpdateOrSave(List<OfficeDutyPlace> insertList,List<OfficeDutyPlace> updateList);

	/**
	 * 更新office_duty_place
	 * @param officeDutyPlace
	 * @return
	 */
	public Integer update(OfficeDutyPlace officeDutyPlace);

	/**
	 * 根据id获取office_duty_place
	 * @param id
	 * @return
	 */
	public OfficeDutyPlace getOfficeDutyPlaceById(String id);

	/**
	 * 根据ids数组查询office_duty_placemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDutyPlace> getOfficeDutyPlaceMapByIds(String[] ids);

	/**
	 * 获取office_duty_place列表
	 * @return
	 */
	public List<OfficeDutyPlace> getOfficeDutyPlaceList();

	/**
	 * 分页获取office_duty_place列表
	 * @param page
	 * @return
	 */
	public List<OfficeDutyPlace> getOfficeDutyPlacePage(Pagination page);

	/**
	 * 根据UnitId获取office_duty_place列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_duty_place
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndUserId(String unitId,String userId,String dutyApplyId,Date dutyTime);
	
	public Map<String,OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndOthers(String unitId,String setId,Date startTime,Date endTime);
	
	public Map<String,List<String>> getOfficeDMap(String unitId,String setId);
}