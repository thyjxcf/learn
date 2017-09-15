package net.zdsoft.office.dutyinformation.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_duty_apply
 * @author 
 * 
 */
public class OfficeDutyApply implements Serializable{
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
	private String dutyInformationId;
	/**
	 * 
	 */
	private String userId;
	private String userName;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 
	 */
	private String type;

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
	public void setDutyInformationId(String dutyInformationId){
		this.dutyInformationId = dutyInformationId;
	}
	/**
	 * 获取
	 */
	public String getDutyInformationId(){
		return this.dutyInformationId;
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
	/**
	 * 设置
	 */
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 * 获取
	 */
	public Date getApplyDate(){
		return this.applyDate;
	}
	/**
	 * 设置
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public String getType(){
		return this.type;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}