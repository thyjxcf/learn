package net.zdsoft.office.repaire.entity;

import java.io.Serializable;
/**
 * office_repaire_sms
 * @author 
 * 
 */
public class OfficeRepaireSms implements Serializable{
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
	private String thisId;
	/**
	 * 0-不接收短信；1-接收短信
	 */
	private Boolean isNeedSms;
	
	private String typeName;
	private String isNeedSMSStr;

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
	public void setThisId(String thisId){
		this.thisId = thisId;
	}
	/**
	 * 获取
	 */
	public String getThisId(){
		return this.thisId;
	}
	/**
	 * 设置
	 */
	public void setIsNeedSms(Boolean isNeedSms){
		this.isNeedSms = isNeedSms;
	}
	/**
	 * 获取
	 */
	public Boolean getIsNeedSms(){
		return this.isNeedSms;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getIsNeedSMSStr() {
		return isNeedSMSStr;
	}
	public void setIsNeedSMSStr(String isNeedSMSStr) {
		this.isNeedSMSStr = isNeedSMSStr;
	}
	
}