package net.zdsoft.office.teacherAttendance.dao;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceExcludeUser;
/**
 * 不参与考勤统计人员信息
 * @author 
 * 
 */
public interface OfficeAttendanceExcludeUserDao{

	/**
	 * 新增不参与考勤统计人员信息
	 * @param officeAttendanceExcludeUser
	 * @return
	 */
	public OfficeAttendanceExcludeUser save(OfficeAttendanceExcludeUser officeAttendanceExcludeUser);

	/**
	 * 根据ids数组删除不参与考勤统计人员信息
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据unitId数组删除不参与考勤统计人员信息
	 * @param ids
	 * @return
	 */
	public Integer deleteByUnitId(String unitId);
	/**
	 * 根据ids数组删除不参与考勤统计人员信息
	 * @param ids
	 * @return
	 */
	public Integer deleteByUserId(String[] ids);

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
	/**
	 * 批量新增
	 * @param users
	 */
	public void batchSave(List<OfficeAttendanceExcludeUser> users);
}