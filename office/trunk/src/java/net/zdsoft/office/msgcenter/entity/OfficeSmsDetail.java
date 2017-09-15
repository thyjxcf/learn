package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
/**
 * office_sms_detail
 * @author 
 * 
 */
public class OfficeSmsDetail implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String infoId;
	/**
	 * 
	 */
	private String receiverId;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private Integer sendState;

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
	public void setInfoId(String infoId){
		this.infoId = infoId;
	}
	/**
	 * 获取
	 */
	public String getInfoId(){
		return this.infoId;
	}
	/**
	 * 设置
	 */
	public void setReceiverId(String receiverId){
		this.receiverId = receiverId;
	}
	/**
	 * 获取
	 */
	public String getReceiverId(){
		return this.receiverId;
	}
	/**
	 * 设置
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 获取
	 */
	public String getPhone(){
		return this.phone;
	}
	/**
	 * 设置
	 */
	public void setSendState(Integer sendState){
		this.sendState = sendState;
	}
	/**
	 * 获取
	 */
	public Integer getSendState(){
		return this.sendState;
	}
}