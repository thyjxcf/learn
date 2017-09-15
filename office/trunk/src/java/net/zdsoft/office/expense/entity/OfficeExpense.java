package net.zdsoft.office.expense.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.office.officeFlow.dto.HisTask;
/**
 * office_expense
 * @author 
 * 
 */
public class OfficeExpense implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private Float expenseMoney;
	/**
	 * 
	 */
	private String expenseType;
	/**
	 * 
	 */
	private String detail;
	private String deptName;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String flowId;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String applyUserId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Boolean isDeleted;

	//辅助字段
	private String applyUserName;
	private String fileName;
	
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见
	private List<Attachment> attachments=new ArrayList<Attachment>();
	
	private String taskId;
	private String taskName;
	
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
	public void setExpenseMoney(Float expenseMoney){
		this.expenseMoney = expenseMoney;
	}
	/**
	 * 获取
	 */
	public Float getExpenseMoney(){
		return this.expenseMoney;
	}
	/**
	 * 设置
	 */
	public void setExpenseType(String expenseType){
		this.expenseType = expenseType;
	}
	/**
	 * 获取
	 */
	public String getExpenseType(){
		return this.expenseType;
	}
	/**
	 * 设置
	 */
	public void setDetail(String detail){
		this.detail = detail;
	}
	/**
	 * 获取
	 */
	public String getDetail(){
		return this.detail;
	}
	/**
	 * 设置
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public String getState(){
		return this.state;
	}
	/**
	 * 设置
	 */
	public void setFlowId(String flowId){
		this.flowId = flowId;
	}
	/**
	 * 获取
	 */
	public String getFlowId(){
		return this.flowId;
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
	public void setApplyUserId(String applyUserId){
		this.applyUserId = applyUserId;
	}
	/**
	 * 获取
	 */
	public String getApplyUserId(){
		return this.applyUserId;
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
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public List<HisTask> getHisTaskList() {
		return hisTaskList;
	}
	public void setHisTaskList(List<HisTask> hisTaskList) {
		this.hisTaskList = hisTaskList;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}