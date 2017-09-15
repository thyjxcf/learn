package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * roomorder_audit_sms
 * @author 
 * 
 */
public class RoomorderAuditSms implements Serializable{
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
	private String auditorId;
	/**
	 * 
	 */
	private String needSms;

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
	public void setAuditorId(String auditorId){
		this.auditorId = auditorId;
	}
	/**
	 * 获取
	 */
	public String getAuditorId(){
		return this.auditorId;
	}
	/**
	 * 设置
	 */
	public void setNeedSms(String needSms){
		this.needSms = needSms;
	}
	/**
	 * 获取
	 */
	public String getNeedSms(){
		return this.needSms;
	}
}