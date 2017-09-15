package net.zdsoft.office.teacherAttendance.dao;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
/**
 * 考勤组
 * @author 
 * 
 */
public interface OfficeAttendanceGroupDao{

	/**
	 * 新增考勤组
	 * @param officeAttendanceGroup
	 * @return
	 */
	public OfficeAttendanceGroup save(OfficeAttendanceGroup officeAttendanceGroup);

	/**
	 * 根据ids数组删除考勤组
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新考勤组
	 * @param officeAttendanceGroup
	 * @return
	 */
	public Integer update(OfficeAttendanceGroup officeAttendanceGroup);

	/**
	 * 根据id获取考勤组
	 * @param id
	 * @return
	 */
	public OfficeAttendanceGroup getOfficeAttendanceGroupById(String id);
	
	/**
	 * 根据name获取考勤组
	 * @param name
	 * @return
	 */
	public OfficeAttendanceGroup getOfficeAttendanceGroupByName(String name ,String id);
	
	/**
	 * 根据办公地点id获取对应的考勤组list
	 * @param placeId
	 * @return
	 */
	public List<OfficeAttendanceGroup> getListByPlaceId(String placeId);
	
	/**
	 * 通过unitId 获取考勤列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeAttendanceGroup> listOfficeAttendanceGroupByUnitId(String unitId);
	
	
	/**
	 * 获取非考勤组
	 * @param id
	 * @return
	 */
	public OfficeAttendanceGroup getOfficeNotAddAttendanceGroup();

}