package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
/**
 * office_executive_fixed_dept
 * @author 
 * 
 */
public class OfficeExecutiveFixedDept implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String deptId;

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
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}
	/**
	 * 获取
	 */
	public String getDeptId(){
		return this.deptId;
	}
}