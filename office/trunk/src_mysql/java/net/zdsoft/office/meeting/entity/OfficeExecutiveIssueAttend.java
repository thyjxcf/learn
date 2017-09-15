package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
/**
 * office_executive_issue_attend
 * @author 
 * 
 */
public class OfficeExecutiveIssueAttend implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String issueId;
	/**
	 * 1：主办科室，2：列席科室，3：提报领导，4：意见征集科室
	 */
	private Integer type;
	/**
	 * 
	 */
	private String objectId;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String replyInfo;
	/**
	 * 
	 */
	private Boolean isReplyed;
	
	private String unitId;

	//辅助字段
	private String objectName;
	
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
	public void setIssueId(String issueId){
		this.issueId = issueId;
	}
	/**
	 * 获取
	 */
	public String getIssueId(){
		return this.issueId;
	}
	/**
	 * 设置1：主办科室，2：列席科室，3：提报领导，4：意见征集科室
	 */
	public void setType(Integer type){
		this.type = type;
	}
	/**
	 * 获取1：主办科室，2：列席科室，3：提报领导，4：意见征集科室
	 */
	public Integer getType(){
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
	/**
	 * 设置
	 */
	public void setReplyInfo(String replyInfo){
		this.replyInfo = replyInfo;
	}
	/**
	 * 获取
	 */
	public String getReplyInfo(){
		return this.replyInfo;
	}
	/**
	 * 设置
	 */
	public void setIsReplyed(Boolean isReplyed){
		this.isReplyed = isReplyed;
	}
	/**
	 * 获取
	 */
	public Boolean getIsReplyed(){
		return this.isReplyed;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}