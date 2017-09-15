package net.zdsoft.office.expenditure.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * office_expenditure_kj_opinion
 * @author 
 * 
 */
public class OfficeExpenditureKjOpinion extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String taskId;
	/**
	 * 是否已列入年度预算明细
	 */
	//private Boolean isPlan;
	private Integer plan;
	/**
	 * 是否同意开支状态
	 */
	//private Boolean isAgreeState;
	private String agreeState;
	/**
	 * 开支费名称
	 */
	private String feeName;
	/**
	 * 节余
	 */
	private Double surplus;
	/**
	 * 节余率
	 */
	private Double surplusRate;
	/**
	 * 开支类型
	 */
	private String payType;
	
	//辅助字段
	private String auditUserName;
	private Date auditDate;
	
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	/**
	 * 设置
	 */
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	/**
	 * 获取
	 */
	public String getTaskId(){
		return this.taskId;
	}
	/**
	 * 设置
	 */
	public void setFeeName(String feeName){
		this.feeName = feeName;
	}
	/**
	 * 获取
	 */
	public String getFeeName(){
		return this.feeName;
	}
	/**
	 * 设置
	 */
	/**
	 * 设置
	 */
	public void setPayType(String payType){
		this.payType = payType;
	}
	/**
	 * 获取
	 */
	public String getPayType(){
		return this.payType;
	}
	
	public String getAgreeState() {
		return agreeState;
	}
	public void setAgreeState(String agreeState) {
		this.agreeState = agreeState;
	}
	public Integer getPlan() {
		return plan;
	}
	public void setPlan(Integer plan) {
		this.plan = plan;
	}
	public Double getSurplus() {
		return surplus;
	}
	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}
	public Double getSurplusRate() {
		return surplusRate;
	}
	public void setSurplusRate(Double surplusRate) {
		this.surplusRate = surplusRate;
	}
	
	
}