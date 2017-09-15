package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * office_meeting_user
 * @author 
 * 
 */
public class OfficeMeetingUser implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String meetingApplyId;
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
	public void setMeetingApplyId(String meetingApplyId){
		this.meetingApplyId = meetingApplyId;
	}
	/**
	 * 获取
	 */
	public String getMeetingApplyId(){
		return this.meetingApplyId;
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