package net.zdsoft.office.teacherAttendance.service;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
/**
 * 考勤组成员
 * @author 
 * 
 */
public interface OfficeAttendanceGroupUserService{

	/**
	 * 新增考勤组成员
	 * @param officeAttendanceGroupUser
	 * @return
	 */
	public OfficeAttendanceGroupUser save(OfficeAttendanceGroupUser officeAttendanceGroupUser);

	/**
	 * 根据ids数组删除考勤组成员数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据ids数组删除考勤组成员数据
	 * @param ids
	 * @return
	 */
	public Integer deleteByGroupId(String groupId);
	

	/**
	 * 更新考勤组成员
	 * @param officeAttendanceGroupUser
	 * @return
	 */
	public Integer update(OfficeAttendanceGroupUser officeAttendanceGroupUser);

	/**
	 * 根据id获取考勤组成员
	 * @param id
	 * @return
	 */
	public OfficeAttendanceGroupUser getOfficeAttendanceGroupUserById(String id);
	
	/**
	 * 根据userId获取对象
	 * @param userId
	 * @return
	 */
	public OfficeAttendanceGroupUser getItemByUserId(String userId);
	
	/**
	 * 批量保存考勤组成员
	 * @param groupUsers
	 */
	public void batchSave(List<OfficeAttendanceGroupUser> groupUsers);
	
	/**
	 * 获取以组id 为键 获取map
	 * @return
	 */
	public Map<String,List<OfficeAttendanceGroupUser>> getOfficeAttendanceGroupUserMap(String[] groupIds);
	
	/**
	 * 删除 
	 * @param GroupId
	 * @param UserId
	 * @return
	 */
	public Integer deleteByGroupIdAndUserId(String groupId,String userId);
	
	/**
	 * 获取组成员列表
	 * @return
	 */
	public List<OfficeAttendanceGroupUser> getOfficeAttendanceGroupUser();
	/**
	 * 根据userIds 获取已userId为key的map
	 * @return
	 */
	public Map<String,String> getGroupUser(String[] userIds);

}