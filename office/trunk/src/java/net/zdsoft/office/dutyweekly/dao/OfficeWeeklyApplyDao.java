package net.zdsoft.office.dutyweekly.dao;

import java.util.*;

import net.zdsoft.office.dutyweekly.entity.OfficeWeeklyApply;
import net.zdsoft.keel.util.Pagination;
/**
 * office_weekly_apply
 * @author 
 * 
 */
public interface OfficeWeeklyApplyDao{

	/**
	 * 新增office_weekly_apply
	 * @param officeWeeklyApply
	 * @return
	 */
	public OfficeWeeklyApply save(OfficeWeeklyApply officeWeeklyApply);

	/**
	 * 根据ids数组删除office_weekly_apply
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_weekly_apply
	 * @param officeWeeklyApply
	 * @return
	 */
	public Integer update(OfficeWeeklyApply officeWeeklyApply);

	/**
	 * 根据id获取office_weekly_apply
	 * @param id
	 * @return
	 */
	public OfficeWeeklyApply getOfficeWeeklyApplyById(String id);

	/**
	 * 根据ids数组查询office_weekly_applymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWeeklyApply> getOfficeWeeklyApplyMapByIds(String[] ids);

	/**
	 * 获取office_weekly_apply列表
	 * @return
	 */
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyList();

	/**
	 * 分页获取office_weekly_apply列表
	 * @param page
	 * @return
	 */
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyPage(Pagination page);

	/**
	 * 根据unitId获取office_weekly_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_weekly_apply获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeWeeklyApply> getOfficeWeeklyAppliesByUnitId(String unitId,String[] projectId);
	
	public void deleteRecordes(String unitId,String dutyWeeklyId,Date dutyDate);
	
	public void batchSave(List<OfficeWeeklyApply> officeWeeklyApply);
	
	public Map<String,OfficeWeeklyApply> getOfficeWeeklyApplyMapByUnitIdAndWeeklyId(String unitId,String weeklyId,Date dutyDate);
	
	public Map<String, OfficeWeeklyApply> getOfficeCountMapByUnitIdAndWeeklyId(String unitId, String weeklyId, Date dutyDate);
	
	public Map<String, OfficeWeeklyApply> getOfficeWMapCount(String unitId, String weeklyId,Date[] dutyTime);
	
	public List<OfficeWeeklyApply> getOfficeWMap(String unitId,String acadayear,String smster,String week,Date startTime,Date endTime,Date[] dutyTime);
	
	public List<OfficeWeeklyApply> getOfficeWeeklyApplies(String unitId,String weeklyId);
}