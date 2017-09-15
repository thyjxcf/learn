package net.zdsoft.office.dutyweekly.service;

import java.util.*;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyRemark;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_remark
 * @author 
 * 
 */
public interface OfficeDutyRemarkService{

	/**
	 * 新增office_duty_remark
	 * @param officeDutyRemark
	 * @return
	 */
	public OfficeDutyRemark save(OfficeDutyRemark officeDutyRemark);

	/**
	 * 根据ids数组删除office_duty_remark数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_duty_remark
	 * @param officeDutyRemark
	 * @return
	 */
	public Integer update(OfficeDutyRemark officeDutyRemark);

	/**
	 * 根据id获取office_duty_remark
	 * @param id
	 * @return
	 */
	public OfficeDutyRemark getOfficeDutyRemarkById(String id);

	/**
	 * 根据ids数组查询office_duty_remarkmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDutyRemark> getOfficeDutyRemarkMapByIds(String[] ids);

	/**
	 * 获取office_duty_remark列表
	 * @return
	 */
	public List<OfficeDutyRemark> getOfficeDutyRemarkList();

	/**
	 * 分页获取office_duty_remark列表
	 * @param page
	 * @return
	 */
	public List<OfficeDutyRemark> getOfficeDutyRemarkPage(Pagination page);

	/**
	 * 根据UnitId获取office_duty_remark列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyRemark> getOfficeDutyRemarkByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_duty_remark
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyRemark> getOfficeDutyRemarkByUnitIdPage(String unitId, Pagination page);
	
	public void deleteOfficeDutyRemark(String unitId,String worklyId,Date dutyDate);
	
	public OfficeDutyRemark getOfficeDutyRemarkByUnitIdAndOthers(String unitId,String worklyId,Date dutyDate);
	
	public List<OfficeDutyRemark> getOfficeDutyRemarksAndUnitIdAndMore(String unitId,String worklyId,Date[] dutyDate);
}