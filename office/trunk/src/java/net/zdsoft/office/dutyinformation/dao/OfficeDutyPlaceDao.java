package net.zdsoft.office.dutyinformation.dao;

import java.util.*;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyPlace;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_place
 * @author 
 * 
 */
public interface OfficeDutyPlaceDao{

	/**
	 * 新增office_duty_place
	 * @param officeDutyPlace
	 * @return
	 */
	public OfficeDutyPlace save(OfficeDutyPlace officeDutyPlace);

	/**
	 * 根据ids数组删除office_duty_place
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void batchSave(List<OfficeDutyPlace> officeDutyPlaces);
	
	public void batchUpdate(List<OfficeDutyPlace> officeDutyPlaces);
	
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
	 * 根据unitId获取office_duty_place列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_duty_place获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndUserId(String unitId,String userId,String dutyApplyId,Date dutyTime);
	
	public Map<String,OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndOthers(String unitId,String setId,Date startTime,Date endTime);
	
	public List<OfficeDutyPlace> getOfficeDMap(String unitId,String setId);
}