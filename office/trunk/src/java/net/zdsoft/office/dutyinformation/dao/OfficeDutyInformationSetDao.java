package net.zdsoft.office.dutyinformation.dao;

import java.util.*;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSet;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_information_set
 * @author 
 * 
 */
public interface OfficeDutyInformationSetDao{

	/**
	 * 新增office_duty_information_set
	 * @param officeDutyInformationSet
	 * @return
	 */
	public OfficeDutyInformationSet save(OfficeDutyInformationSet officeDutyInformationSet);

	/**
	 * 根据ids数组删除office_duty_information_set
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_duty_information_set
	 * @param officeDutyInformationSet
	 * @return
	 */
	public Integer update(OfficeDutyInformationSet officeDutyInformationSet);

	/**
	 * 根据id获取office_duty_information_set
	 * @param id
	 * @return
	 */
	public OfficeDutyInformationSet getOfficeDutyInformationSetById(String id);
	
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetsByIds(String[] ids);

	/**
	 * 根据ids数组查询office_duty_information_setmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDutyInformationSet> getOfficeDutyInformationSetMapByIds(String[] ids);

	/**
	 * 获取office_duty_information_set列表
	 * @return
	 */
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetList();

	/**
	 * 分页获取office_duty_information_set列表
	 * @param page
	 * @return
	 */
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetPage(Pagination page);

	/**
	 * 根据unitId获取office_duty_information_set列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_duty_information_set获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetsByUnitIdName(String unitId,String year,String dutyName,Pagination page);
	
	public boolean isExistConflict(String unitId,String dutyName,String dutyId);
}