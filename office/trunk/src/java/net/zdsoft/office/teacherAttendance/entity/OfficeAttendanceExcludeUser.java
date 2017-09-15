package net.zdsoft.office.teacherAttendance.entity;

import java.io.Serializable;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * 不参与考勤统计人员信息
 * @author 
 * 
 */
public class OfficeAttendanceExcludeUser extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String userId;
	
	

	/**
	 * 设置
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
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