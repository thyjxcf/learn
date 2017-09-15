package net.zdsoft.office.dutyinformation.entity;

import java.io.Serializable;
/**
 * office_duty_information_set_ex
 * @author 
 * 
 */
public class OfficeDutyInformationSetEx implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String dutyInformationId;
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
}