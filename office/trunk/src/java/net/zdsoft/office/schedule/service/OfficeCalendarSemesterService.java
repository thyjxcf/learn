package net.zdsoft.office.schedule.service;

import java.util.*;
import net.zdsoft.office.schedule.entity.OfficeCalendarSemester;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_semester
 * @author 
 * 
 */
public interface OfficeCalendarSemesterService{

	/**
	 * 新增office_calendar_semester
	 * @param officeCalendarSemester
	 * @return
	 */
	public OfficeCalendarSemester save(OfficeCalendarSemester officeCalendarSemester);

	/**
	 * 根据ids数组删除office_calendar_semester数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_calendar_semester
	 * @param officeCalendarSemester
	 * @return
	 */
	public Integer update(OfficeCalendarSemester officeCalendarSemester);

	/**
	 * 根据id获取office_calendar_semester
	 * @param id
	 * @return
	 */
	public OfficeCalendarSemester getOfficeCalendarSemesterById(String id);

	/**
	 * 根据ids数组查询office_calendar_semestermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCalendarSemester> getOfficeCalendarSemesterMapByIds(String[] ids);

	/**
	 * 获取office_calendar_semester列表
	 * @return
	 */
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterList();

	/**
	 * 分页获取office_calendar_semester列表
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterPage(Pagination page);

	/**
	 * 根据UnitId获取office_calendar_semester列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_calendar_semester
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterByUnitIdPage(String unitId, Pagination page);

	public OfficeCalendarSemester getCalendarSemester(String calyear,
			String unitId);

	public OfficeCalendarSemester getCalendarSemester(String acadyear,
			String semester, String unitId);

	public void updateContent(OfficeCalendarSemester officeCalendarSemester);
}