package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
/**
 * office_work_meeting_attend
 * @author 
 * 
 */
public class OfficeWorkMeetingAttend implements Serializable{
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
	 * 1：主办科室，2：列席科室，3：参会局领导
	 */
	private int type;
	
    public static final int TYPE_HOST_DEPT = 1;//主办科室
    public static final int TYPE_OTHER_DEPT = 2;//列席科室
    public static final int TYPE_LEADER = 3;//参会局领导
	/**
	 * 
	 */
	private String objectId;
	
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
	 * 设置1：主办科室，2：列席科室，3：参会局领导
	 */
	public void setType(int type){
		this.type = type;
	}
	/**
	 * 获取1：主办科室，2：列席科室，3：参会局领导
	 */
	public int getType(){
		return this.type;
	}
	/**
	 * 设置
	 */
	public void setObjectId(String objectId){
		this.objectId = objectId;
	}
	/**
	 * 获取
	 */
	public String getObjectId(){
		return this.objectId;
	}
}