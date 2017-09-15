package net.zdsoft.office.teacherAttendance.service;


import java.util.*;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceExcludeUser;
/**
 * 不参与考勤统计人员信息
 * @author 
 * 
 */
public interface OfficeAttendanceExcludeUserService{

	/**
	 * 新增不参与考勤统计人员信息
	 * @param officeAttendanceExcludeUser
	 * @return
	 */
	public OfficeAttendanceExcludeUser save(OfficeAttendanceExcludeUser officeAttendanceExcludeUser);
	
	/**
	 * 新增不参与考勤统计人员信息
	 * @param officeAttendanceExcludeUser
	 * @return
	 */
	public void save(String unitId,List<User> users);

	/**
	 * 根据ids数组删除不参与考勤统计人员信息数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新不参与考勤统计人员信息
	 * @param officeAttendanceExcludeUser
	 * @return
	 */
	public Integer update(OfficeAttendanceExcludeUser officeAttendanceExcludeUser);

	/**
	 * 根据id获取不参与考勤统计人员信息
	 * @param id
	 * @return
	 */
	public OfficeAttendanceExcludeUser getOfficeAttendanceExcludeUserById(String id);
	/**
	 * 通过 unitId 获取list;
	 * @param unitId
	 * @return
	 */
	public List<OfficeAttendanceExcludeUser> getOfficeAttendanceExcludeUserByUnitId(String unitId);
	/*
	 * 根据ids数组删除不参与考勤统计人员信息
	 * @param ids
	 * @return
	 */
	public Integer deleteByUserId(String[] ids);

}