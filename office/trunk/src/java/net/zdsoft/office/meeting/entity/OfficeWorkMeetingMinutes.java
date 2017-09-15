package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.atlassian.mail.MailUtils.Attachment;
/**
 * office_work_meeting_minutes
 * @author 
 * 
 */
public class OfficeWorkMeetingMinutes implements Serializable{
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
	private String content;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 
	 */
	private Date createTime;
	
	//--多个附件
	public static final String OFFICEWORKMEETINGMINUTES_ATTACHMENT="OFFICEWORKMEETINGMINUTES_ATTACHMENT";//附件类型
	private Integer hasAttached;
	private List<Attachment> attachments=new ArrayList<Attachment>();
	private String unitId;
	
	//单个附件
	private String fileName;//附件名称
	private String fileUrl;//附件地址
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
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取
	 */
	public String getContent(){
		return this.content;
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
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public Integer getHasAttached() {
		return hasAttached;
	}
	public void setHasAttached(Integer hasAttached) {
		this.hasAttached = hasAttached;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	
}