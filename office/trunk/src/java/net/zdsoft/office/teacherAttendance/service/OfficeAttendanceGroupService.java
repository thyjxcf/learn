package net.zdsoft.office.teacherAttendance.service;


import java.util.*;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
/**
 * 考勤组
 * @author 
 * 
 */
public interface OfficeAttendanceGroupService{

	/**
	 * 新增考勤组
	 * @param officeAttendanceGroup
	 * @return
	 */
	public OfficeAttendanceGroup save(OfficeAttendanceGroup officeAttendanceGroup);
	
	/**
	 * 新增考勤组
	 * @param officeAttendanceGroup
	 * @return
	 */
	public PromptMessageDto save(OfficeAttendanceGroup officeAttendanceGroup ,List<User> users);

	/**
	 * 根据ids数组删除考勤组数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void deleteGroupAndUser(String groupId);

	/**
	 * 更新考勤组
	 * @param officeAttendanceGroup
	 * @return
	 */
	public Integer update(OfficeAttendanceGroup officeAttendanceGroup);
	/**
	 * 更新考勤组
	 * @param officeAttendanceGroup
	 * @return
	 */
	public Integer update(OfficeAttendanceGroup officeAttendanceGroup,List<User> users);

	/**
	 * 根据id获取考勤组
	 * @param id
	 * @return
	 */
	public OfficeAttendanceGroup getOfficeAttendanceGroupById(String id);
	
	/**
	 * 获取非考勤组
	 * @param id
	 * @return
	 */
	public OfficeAttendanceGroup getOfficeNotAddAttendanceGroup();
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
	
	public List<OfficeAttendanceGroup> listOfficeAttendanceGroupByUnitId(String unitId);

}