package net.zdsoft.office.expenditure.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.office.officeFlow.dto.HisTask;
/**
 * office_expenditure（财务开支）
 * @author 
 * 
 */
public class OfficeExpenditure extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String applyUserId;
	private String applyUserName;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 财务开支类型（会议费，公务接待费等）
	 */
	private String type;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String flowId;
	
	private String taskId;
	private String taskName;
	//申请部门
	private String applyUserDeptName;
	private Double fee;//合计
	private boolean kj;//是否财务审核
	
	private boolean chg;//车管
	
	private Date createTime;
	
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见
	private List<HisTask> otherHisTask=new ArrayList<HisTask>();//流程意见
	private Map<String, OfficeExpenditureKjOpinion> kjOpinionMap;//会计审核
	private Map<String, OfficeExpenditureChgOpinion> chgOpinionMap;//车管意见
	

	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public List<HisTask> getOtherHisTask() {
		return otherHisTask;
	}
	public void setOtherHisTask(List<HisTask> otherHisTask) {
		this.otherHisTask = otherHisTask;
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
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 * 获取
	 */
	public Date getApplyDate(){
		return this.applyDate;
	}
	/**
	 * 设置
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public String getType(){
		return this.type;
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
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
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
	public List<HisTask> getHisTaskList() {
		return hisTaskList;
	}
	public void setHisTaskList(List<HisTask> hisTaskList) {
		this.hisTaskList = hisTaskList;
	}
	public boolean isKj() {
		return kj;
	}
	public void setKj(boolean kj) {
		this.kj = kj;
	}
	public boolean isChg() {
		return chg;
	}
	public void setChg(boolean chg) {
		this.chg = chg;
	}
	public String getApplyUserDeptName() {
		return applyUserDeptName;
	}
	public void setApplyUserDeptName(String applyUserDeptName) {
		this.applyUserDeptName = applyUserDeptName;
	}
	public Map<String, OfficeExpenditureKjOpinion> getKjOpinionMap() {
		if(kjOpinionMap==null){
			return new HashMap<String, OfficeExpenditureKjOpinion>();
		}
		return kjOpinionMap;
	}
	public void setKjOpinionMap(Map<String, OfficeExpenditureKjOpinion> kjOpinionMap) {
		this.kjOpinionMap = kjOpinionMap;
	}
	public Map<String, OfficeExpenditureChgOpinion> getChgOpinionMap() {
		if(chgOpinionMap==null){
			return new HashMap<String, OfficeExpenditureChgOpinion>();
		}
		return chgOpinionMap;
	}
	public void setChgOpinionMap(
			Map<String, OfficeExpenditureChgOpinion> chgOpinionMap) {
		this.chgOpinionMap = chgOpinionMap;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}