package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_work_meeting_confirm
 * @author 
 * 
 */
public class OfficeWorkMeetingConfirm implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String meetingId;
	/**
	 * 
	 */
	private String attendUserId;
	/**
	 * 
	 */
	private String transferUserId;
	/**
	 * 0： 未定
1：确认参会
2：确认不参会
3：转交他人
	 */
	private int attendType;
	
	public static final int TYPE_ZERO = 0;//未确定
	public static final int TYPE_ATTEND = 1;//参会
    public static final int TYPE_NOT_ATTEND = 2;//不参会
    public static final int TYPE_TRANSFER = 3;//转交他人
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String remark;

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
	public void setMeetingId(String meetingId){
		this.meetingId = meetingId;
	}
	/**
	 * 获取
	 */
	public String getMeetingId(){
		return this.meetingId;
	}
	/**
	 * 设置
	 */
	public void setAttendUserId(String attendUserId){
		this.attendUserId = attendUserId;
	}
	/**
	 * 获取
	 */
	public String getAttendUserId(){
		return this.attendUserId;
	}
	/**
	 * 设置
	 */
	public void setTransferUserId(String transferUserId){
		this.transferUserId = transferUserId;
	}
	/**
	 * 获取
	 */
	public String getTransferUserId(){
		return this.transferUserId;
	}
	/**
	 * 设置0： 未定
1：确认参会
2：确认不参会
3：转交他人
	 */
	public void setAttendType(int attendType){
		this.attendType = attendType;
	}
	/**
	 * 获取0： 未定
1：确认参会
2：确认不参会
3：转交他人
	 */
	public int getAttendType(){
		return this.attendType;
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
}