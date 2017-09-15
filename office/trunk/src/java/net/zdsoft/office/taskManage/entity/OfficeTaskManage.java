package net.zdsoft.office.taskManage.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * office_task_manage
 * @author 
 * 
 */
public class OfficeTaskManage implements Serializable{
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
	private String taskName;
	/**
	 * 
	 */
	private String dealUserId;
	/**
	 * 
	 */
	private Date completeTime;
	/**
	 * 
	 */
	private Integer hasAttach;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 1：未发布，2：已发布，3：已完成
	 */
	private Integer state;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date firstRemindTime;
	/**
	 * 
	 */
	private Date secondRemindTime;
	/**
	 * 
	 */
	private Integer remindNumber;
	/**
	 * 
	 */
	private Date actualFinishTime;
	/**
	 * 
	 */
	private Integer hasSubmitAttach;
	/**
	 * 
	 */
	private String finishRemark;
	/**
	 * 
	 */
	private Boolean isDeleted;

	//辅助字段
	private String dealUserName;
	private Attachment leaderAttachment;
	private Attachment dealUserAttachment;
	private Boolean isDelay;
	private String leaderFileName;
	private String dealUserFileName;
	private String leaderFileURL;
	private String dealUserFileURL;
	
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
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	/**
	 * 获取
	 */
	public String getTaskName(){
		return this.taskName;
	}
	/**
	 * 设置
	 */
	public void setDealUserId(String dealUserId){
		this.dealUserId = dealUserId;
	}
	/**
	 * 获取
	 */
	public String getDealUserId(){
		return this.dealUserId;
	}
	/**
	 * 设置
	 */
	public void setCompleteTime(Date completeTime){
		this.completeTime = completeTime;
	}
	/**
	 * 获取
	 */
	public Date getCompleteTime(){
		return this.completeTime;
	}
	/**
	 * 设置
	 */
	public void setHasAttach(Integer hasAttach){
		this.hasAttach = hasAttach;
	}
	/**
	 * 获取
	 */
	public Integer getHasAttach(){
		return this.hasAttach;
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
	 * 设置1：未发布，2：已发布，3：已完成
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取1：未发布，2：已发布，3：已完成
	 */
	public Integer getState(){
		return this.state;
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
	/**
	 * 设置
	 */
	public void setFirstRemindTime(Date firstRemindTime){
		this.firstRemindTime = firstRemindTime;
	}
	/**
	 * 获取
	 */
	public Date getFirstRemindTime(){
		return this.firstRemindTime;
	}
	/**
	 * 设置
	 */
	public void setSecondRemindTime(Date secondRemindTime){
		this.secondRemindTime = secondRemindTime;
	}
	/**
	 * 获取
	 */
	public Date getSecondRemindTime(){
		return this.secondRemindTime;
	}
	/**
	 * 设置
	 */
	public void setRemindNumber(Integer remindNumber){
		this.remindNumber = remindNumber;
	}
	/**
	 * 获取
	 */
	public Integer getRemindNumber(){
		return this.remindNumber;
	}
	/**
	 * 设置
	 */
	public void setActualFinishTime(Date actualFinishTime){
		this.actualFinishTime = actualFinishTime;
	}
	/**
	 * 获取
	 */
	public Date getActualFinishTime(){
		return this.actualFinishTime;
	}
	/**
	 * 设置
	 */
	public void setHasSubmitAttach(Integer hasSubmitAttach){
		this.hasSubmitAttach = hasSubmitAttach;
	}
	/**
	 * 获取
	 */
	public Integer getHasSubmitAttach(){
		return this.hasSubmitAttach;
	}
	/**
	 * 设置
	 */
	public void setFinishRemark(String finishRemark){
		this.finishRemark = finishRemark;
	}
	/**
	 * 获取
	 */
	public String getFinishRemark(){
		return this.finishRemark;
	}
	/**
	 * 设置
	 */
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
	}
	
	public String getDealUserName() {
		return dealUserName;
	}
	
	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}
	
	public Attachment getLeaderAttachment() {
		return leaderAttachment;
	}
	
	public void setLeaderAttachment(Attachment leaderAttachment) {
		this.leaderAttachment = leaderAttachment;
	}
	
	public Attachment getDealUserAttachment() {
		return dealUserAttachment;
	}
	
	public void setDealUserAttachment(Attachment dealUserAttachment) {
		this.dealUserAttachment = dealUserAttachment;
	}
	
	public Boolean getIsDelay() {
		return isDelay;
	}
	
	public void setIsDelay(Boolean isDelay) {
		this.isDelay = isDelay;
	}
	
	public String getLeaderFileURL() {
		return leaderFileURL;
	}
	
	public void setLeaderFileURL(String leaderFileURL) {
		this.leaderFileURL = leaderFileURL;
	}
	
	public String getDealUserFileURL() {
		return dealUserFileURL;
	}
	
	public void setDealUserFileURL(String dealUserFileURL) {
		this.dealUserFileURL = dealUserFileURL;
	}
	public String getLeaderFileName() {
		return leaderFileName;
	}
	public void setLeaderFileName(String leaderFileName) {
		this.leaderFileName = leaderFileName;
	}
	public String getDealUserFileName() {
		return dealUserFileName;
	}
	public void setDealUserFileName(String dealUserFileName) {
		this.dealUserFileName = dealUserFileName;
	}
	
}