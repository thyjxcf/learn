package net.zdsoft.office.dutyinformation.service;

import java.util.*;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyApply;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_apply
 * @author 
 * 
 */
public interface OfficeDutyApplyService{

	/**
	 * 新增office_duty_apply
	 * @param officeDutyApply
	 * @return
	 */
	public OfficeDutyApply save(OfficeDutyApply officeDutyApply);

	/**
	 * 根据ids数组删除office_duty_apply数据
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
	 * 根据UnitId获取office_duty_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_duty_apply
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdPage(String unitId, Pagination page);
	
	public Map<String, OfficeDutyApply> getOfficeDutyAppliesMap(String unitId,String dutyId);
	
	public boolean saveOfficeDutyApplyInfo(String[] applyRooms,String dutyId,String unitId,String userId,boolean admin);
	
	/**
	 * 查询值班记录
	 */
	public List<OfficeDutyApply> getOfficeDutyAppliesByUnitIdAndUserId(String unitId,Date applyDate,String userId);
}