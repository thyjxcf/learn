package net.zdsoft.office.dutyinformation.dao;

import java.util.*;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyApply;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_apply
 * @author 
 * 
 */
public interface OfficeDutyApplyDao{

	/**
	 * 新增office_duty_apply
	 * @param officeDutyApply
	 * @return
	 */
	public OfficeDutyApply save(OfficeDutyApply officeDutyApply);

	/**
	 * 根据ids数组删除office_duty_apply
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_duty_apply
	 * @param officeDutyApply
	 * @return
	 */
	public Integer update(OfficeDutyApply officeDutyApply);

	/**
	 * 根据id获取office_duty_apply
	 * @param id
	 * @return
	 */
	public OfficeDutyApply getOfficeDutyApplyById(String id);

	/**
	 * 根据ids数组查询office_duty_applymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDutyApply> getOfficeDutyApplyMapByIds(String[] ids);

	/**
	 * 获取office_duty_apply列表
	 * @return
	 */
	public List<OfficeDutyApply> getOfficeDutyApplyList();

	/**
	 * 分页获取office_duty_apply列表
	 * @param page
	 * @return
	 */
	public List<OfficeDutyApply> getOfficeDutyApplyPage(Pagination page);

	/**
	 * 根据unitId获取office_duty_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_duty_apply获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeDutyApply> getOfficeDutyAppliesMap(String unitId,String dutyId);
	
	public boolean isApplyByOthers(String unitId,String dutyId, String type, String userId, Date date);
	public boolean isApplyAdmin(String unitId,String dutyId, String userId, Date date);
	
	public void deleteApplyInfo(String unitId,String dutyId,String[] userId);
	
	public void batchSaveApplyInfo(List<OfficeDutyApply> officeDutyApplies);
	
	public List<OfficeDutyApply> getOfficeDutyAppliesByUnitIdAndUserId(String unitId,Date applyDate,String userId);
}