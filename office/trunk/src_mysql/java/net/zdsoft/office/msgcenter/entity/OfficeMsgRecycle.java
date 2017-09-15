package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 信息回收
 * @author 
 * 
 */
public class OfficeMsgRecycle implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 相关编号
	 */
	private String referenceId;
	/**
	 * 删除时间
	 */
	private Date deleteTime;
	/**
	 * 状态,即未删除前在哪个文件夹中。0-收件箱，1-发件箱，2-草稿箱，3-废件箱，4-自定义
	 */
	private Integer state;
	/**
	 * 定制文件夹编号
	 */
	private String customFolderId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 紧急
	 */
	private Integer isEmergency;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 1 留言 2 通知 
	 */
	private Integer msgtype;
	
	private String dateStr;//日期格式（星期一	 01-01）
	
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
	 * 设置相关编号
	 */
	public void setReferenceId(String referenceId){
		this.referenceId = referenceId;
	}
	/**
	 * 获取相关编号
	 */
	public String getReferenceId(){
		return this.referenceId;
	}
	/**
	 * 设置删除时间
	 */
	public void setDeleteTime(Date deleteTime){
		this.deleteTime = deleteTime;
	}
	/**
	 * 获取删除时间
	 */
	public Date getDeleteTime(){
		return this.deleteTime;
	}
	/**
	 * 设置状态,即未删除前在哪个文件夹中。0-收件箱，1-发件箱，2-草稿箱，3-废件箱，4-自定义
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取状态,即未删除前在哪个文件夹中。0-收件箱，1-发件箱，2-草稿箱，3-废件箱，4-自定义
	 */
	public Integer getState(){
		return this.state;
	}
	/**
	 * 设置定制文件夹编号
	 */
	public void setCustomFolderId(String customFolderId){
		this.customFolderId = customFolderId;
	}
	/**
	 * 获取定制文件夹编号
	 */
	public String getCustomFolderId(){
		return this.customFolderId;
	}
	/**
	 * 设置标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 获取标题
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
	 * 设置发送时间
	 */
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}
	/**
	 * 获取发送时间
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
	public void setMsgtype(Integer msgtype){
		this.msgtype = msgtype;
	}
	/**
	 * 获取1 留言 2 通知 
	 */
	public Integer getMsgtype(){
		return this.msgtype;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
}