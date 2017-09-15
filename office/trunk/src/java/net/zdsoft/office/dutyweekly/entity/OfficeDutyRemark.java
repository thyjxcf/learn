package net.zdsoft.office.dutyweekly.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_duty_remark
 * @author 
 * 
 */
public class OfficeDutyRemark implements Serializable{
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
	private String dutyWeeklyId;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 
	 */
	private String remark;
	private String remarks;
	/**
	 * 
	 */
	private Date createTime;

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
	public void setDutyWeeklyId(String dutyWeeklyId){
		this.dutyWeeklyId = dutyWeeklyId;
	}
	/**
	 * 获取
	 */
	public String getDutyWeeklyId(){
		return this.dutyWeeklyId;
	}
	/**
	 * 设置
	 */
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 * 获取
	 */
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 设置
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 * 获取
	 */
	public String getRemark(){
		return this.remark;
	}
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}