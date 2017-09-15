package net.zdsoft.office.dutyweekly.dao;

import java.util.*;

import net.zdsoft.office.dutyweekly.entity.OfficeDutyProject;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_project
 * @author 
 * 
 */
public interface OfficeDutyProjectDao{

	/**
	 * 新增office_duty_project
	 * @param officeDutyProject
	 * @return
	 */
	public OfficeDutyProject save(OfficeDutyProject officeDutyProject);

	/**
	 * 根据ids数组删除office_duty_project
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
	 * 根据unitId获取office_duty_project列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdList(String unitId);
	
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdListss(String unitId,String years,String semesters);

	/**
	 * 根据unitId分页office_duty_project获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdPage(String unitId, Pagination page);
}