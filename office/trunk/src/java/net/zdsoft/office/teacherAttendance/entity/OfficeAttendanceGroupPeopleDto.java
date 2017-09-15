package net.zdsoft.office.teacherAttendance.entity;
/** 
 * @author  lufeng 
 * @version 创建时间：2017-4-21 上午09:47:05 
 * 类说明 
 */
public class OfficeAttendanceGroupPeopleDto {
	/**
	 * 教师名字
	 */
	private String name;
	/**
	 * 部门
	 */
	private String departmentName;
	/**
	 * 是否参加考勤统计
	 */
	private boolean addAttendancestatistics;
	/**
	 * 用户id
	 */
	private String groupUserId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public boolean isAddAttendancestatistics() {
		return addAttendancestatistics;
	}
	public void setAddAttendancestatistics(boolean addAttendancestatistics) {
		this.addAttendancestatistics = addAttendancestatistics;
	}
	public String getGroupUserId() {
		return groupUserId;
	}
	public void setGroupUserId(String groupUserId) {
		this.groupUserId = groupUserId;
	}
	
	
	
}
