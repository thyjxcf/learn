package net.zdsoft.office.expenditure.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * office_expenditure_chg_opinion
 * @author 
 * 
 */
public class OfficeExpenditureChgOpinion extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String taskId;
	/**
	 * 车辆所属单位
	 */
	private String carUnitName;
	/**
	 * 车牌号
	 */
	private String carNumber;
	/**
	 * 驾驶员姓名
	 */
	private String driverName;
	/**
	 * 用车费用类型
	 */
	private String payType;
	/**
	 * 费用
	 */
	private Double fee;
	
	private Double fee1;
	private Double fee2;
	
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
	public void setCarUnitName(String carUnitName){
		this.carUnitName = carUnitName;
	}
	/**
	 * 获取
	 */
	public String getCarUnitName(){
		return this.carUnitName;
	}
	/**
	 * 设置
	 */
	public void setCarNumber(String carNumber){
		this.carNumber = carNumber;
	}
	/**
	 * 获取
	 */
	public String getCarNumber(){
		return this.carNumber;
	}
	/**
	 * 设置
	 */
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}
	/**
	 * 获取
	 */
	public String getDriverName(){
		return this.driverName;
	}
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
	
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getFee1() {
		return fee1;
	}
	public void setFee1(Double fee1) {
		this.fee1 = fee1;
	}
	public Double getFee2() {
		return fee2;
	}
	public void setFee2(Double fee2) {
		this.fee2 = fee2;
	}
}