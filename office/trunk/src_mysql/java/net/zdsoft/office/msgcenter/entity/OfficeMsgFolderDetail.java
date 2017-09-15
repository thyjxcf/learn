package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 文件夹详细信息
 * @author 
 * 
 */
public class OfficeMsgFolderDetail implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 文件夹编号
	 */
	private String folderId;
	/**
	 * 相关编号来源的id
	 */
	private String referenceId;
	/**
	 * 原始信息的来源箱，1-草稿箱，2，发件箱，3，收件箱，4，废件箱 ５，自定义文件夹
	 */
	private Integer referenceState;
	/**
	 * 创建时间
	 */
	private Date creationTime;
	/**
	 * 
	 */
	private String title;
	/**
	 * 紧急
	 */
	private Integer isEmergency;
	/**
	 * 
	 */
	private Date sendTime;
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 1 留言 2 通知
	 */
	private Integer msgType;
	
	private Boolean isDeleted;
	
	private String dateStr;//日期格式（星期一	 01-01）
	
	private Integer isCopy;//是否拷贝

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
	 * 设置文件夹编号
	 */
	public void setFolderId(String folderId){
		this.folderId = folderId;
	}
	/**
	 * 获取文件夹编号
	 */
	public String getFolderId(){
		return this.folderId;
	}
	/**
	 * 设置相关编号来源的id
	 */
	public void setReferenceId(String referenceId){
		this.referenceId = referenceId;
	}
	/**
	 * 获取相关编号来源的id
	 */
	public String getReferenceId(){
		return this.referenceId;
	}
	/**
	 * 设置原始信息的来源箱，1-草稿箱，2，发件箱，3，收件箱，4，废件箱 ５，自定义文件夹
	 */
	public void setReferenceState(Integer referenceState){
		this.referenceState = referenceState;
	}
	/**
	 * 获取原始信息的来源箱，1-草稿箱，2，发件箱，3，收件箱，4，废件箱 ５，自定义文件夹
	 */
	public Integer getReferenceState(){
		return this.referenceState;
	}
	/**
	 * 设置创建时间
	 */
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	/**
	 * 获取创建时间
	 */
	public Date getCreationTime(){
		return this.creationTime;
	}
	/**
	 * 设置
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 获取
	 */
	public String getTitle(){
		return this.title;
	}
	/**
	 * 设置紧急
	 */
	public void setIsEmergency(Integer isEmergency){
		this.isEmergency = isEmergency;
	}
	/**
	 * 获取紧急
	 */
	public Integer getIsEmergency(){
		return this.isEmergency;
	}
	/**
	 * 设置
	 */
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}
	/**
	 * 获取
	 */
	public Date getSendTime(){
		return this.sendTime;
	}
	/**
	 * 设置用户编号
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取用户编号
	 */
	public String getUserId(){
		return this.userId;
	}
	/**
	 * 设置1 留言 2 通知
	 */
	public void setMsgType(Integer msgType){
		this.msgType = msgType;
	}
	/**
	 * 获取1 留言 2 通知
	 */
	public Integer getMsgType(){
		return this.msgType;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public Integer getIsCopy() {
		return isCopy;
	}
	public void setIsCopy(Integer isCopy) {
		this.isCopy = isCopy;
	}
	
}