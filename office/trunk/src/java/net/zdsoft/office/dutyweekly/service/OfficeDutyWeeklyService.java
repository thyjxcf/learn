package net.zdsoft.office.dutyweekly.service;

import java.util.*;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyWeekly;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_weekly
 * @author 
 * 
 */
public interface OfficeDutyWeeklyService{

	/**
	 * 新增office_duty_weekly
	 * @param officeDutyWeekly
	 * @return
	 */
	public OfficeDutyWeekly save(OfficeDutyWeekly officeDutyWeekly);

	/**
	 * 根据ids数组删除office_duty_weekly数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_duty_weekly
	 * @param officeDutyWeekly
	 * @return
	 */
	public Integer update(OfficeDutyWeekly officeDutyWeekly);

	/**
	 * 根据id获取office_duty_weekly
	 * @param id
	 * @return
	 */
	public OfficeDutyWeekly getOfficeDutyWeeklyById(String id);

	/**
	 * 根据ids数组查询office_duty_weeklymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDutyWeekly> getOfficeDutyWeeklyMapByIds(String[] ids);

	/**
	 * 获取office_duty_weekly列表
	 * @return
	 */
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyList();

	/**
	 * 分页获取office_duty_weekly列表
	 * @param page
	 * @return
	 */
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyPage(Pagination page);

	/**
	 * 根据UnitId获取office_duty_weekly列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_duty_weekly
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdPage(String unitId, Pagination page);
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdPagess(String unitId,String year,String semester, Pagination page);
	
	/**
	 * 判断日期或者周次是否重复
	 */
	public boolean isExistConflict(String unitId,String year,String semester,String id,Integer week,Date stateTime,Date endTime);
	
	/**
	 * 根据日期查对象
	 * @return
	 */
	public OfficeDutyWeekly getOfficeDutyWeeklyByUnitIdAndDate(String unitId,Date dutyDate);
	
	public OfficeDutyWeekly getOfficeDutyWeeklyByUnitIdAnds(String unitId,String year,String semester,String week);
	
	public Map<String,OfficeDutyWeekly> getOfficeDMap(String[] weeklyIds);
	
	public String findMaxWeek(String unitId,String year,String semester);
	
	public OfficeDutyWeekly getFindMaxWeek(String unitId,String year,String semester);
}