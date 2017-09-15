package net.zdsoft.office.teacherAttendance.service;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
/**
 * office_attendance_set(考勤制度设置)
 * @author 
 * 
 */
public interface OfficeAttendanceSetService{

	/**
	 * 新增office_attendance_set(考勤制度设置)
	 * @param officeAttendanceSet
	 * @return
	 */
	public OfficeAttendanceSet save(OfficeAttendanceSet officeAttendanceSet);

	/**
	 * 根据ids数组删除office_attendance_set(考勤制度设置)数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_attendance_set(考勤制度设置)
	 * @param officeAttendanceSet
	 * @return
	 */
	public Integer update(OfficeAttendanceSet officeAttendanceSet);

	/**
	 * 根据id获取office_attendance_set(考勤制度设置)
	 * @param id
	 * @return
	 */
	public OfficeAttendanceSet getOfficeAttendanceSetById(String id);
	/**
	 * 根据unitid获取office_attendance_set(考勤制度设置)
	 * @param unitid
	 * @return
	 */
	public List<OfficeAttendanceSet> getOfficeAttendanceSetByUnitId(String unitId);
	
	
	/**
	 * 根据unitid获取map
	 * @param unitid
	 * @return
	 */
	public Map<String,OfficeAttendanceSet> getOfficeAttendanceSetMapByUnitId(String unitId);
}

