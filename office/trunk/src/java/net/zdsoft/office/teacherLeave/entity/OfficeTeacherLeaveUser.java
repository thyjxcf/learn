package net.zdsoft.office.teacherLeave.entity;

import java.io.Serializable;
/**
 * office_teacher_leave_user
 * @author 
 * 
 */
public class OfficeTeacherLeaveUser implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String leaveId;
	/**
	 * 
	 */
	private String userId;

	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置
	 */
	public void setLeaveId(String leaveId){
		this.leaveId = leaveId;
	}
	/**
	 * 获取
	 */
	public String getLeaveId(){
		return this.leaveId;
	}
	/**
	 * 设置
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取
	 */
	public String getUserId(){
		return this.userId;
	}
}