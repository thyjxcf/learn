package net.zdsoft.office.dutyweekly.service;

import java.util.*;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyProject;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_project
 * @author 
 * 
 */
public interface OfficeDutyProjectService{

	/**
	 * 新增office_duty_project
	 * @param officeDutyProject
	 * @return
	 */
	public OfficeDutyProject save(OfficeDutyProject officeDutyProject);

	/**
	 * 根据ids数组删除office_duty_project数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_duty_project
	 * @param officeDutyProject
	 * @return
	 */
	public Integer update(OfficeDutyProject officeDutyProject);

	/**
	 * 根据id获取office_duty_project
	 * @param id
	 * @return
	 */
	public OfficeDutyProject getOfficeDutyProjectById(String id);

	/**
	 * 根据ids数组查询office_duty_projectmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDutyProject> getOfficeDutyProjectMapByIds(String[] ids);

	/**
	 * 获取office_duty_project列表
	 * @return
	 */
	public List<OfficeDutyProject> getOfficeDutyProjectList();

	/**
	 * 分页获取office_duty_project列表
	 * @param page
	 * @return
	 */
	public List<OfficeDutyProject> getOfficeDutyProjectPage(Pagination page);

	/**
	 * 根据UnitId获取office_duty_project列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdList(String unitId);
	
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdListss(String unitId,String years,String semesters);

	/**
	 * 根据UnitId分页获取office_duty_project
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdPage(String unitId, Pagination page);
}