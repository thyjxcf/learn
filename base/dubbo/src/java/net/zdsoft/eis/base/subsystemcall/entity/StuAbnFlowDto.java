package net.zdsoft.eis.base.subsystemcall.entity;

import java.io.Serializable;
import java.util.Date;

public class StuAbnFlowDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String schId;// 学校id
	private String stuId;// 学生id
	private String flowType;// 异动类型
	private String fromClass;// 来源班级 
	private String toClass;// 去向班级
	private String flowReason;// 原因
	private Date flowDate;// 日期
	private String operator;// 经办人
	public String getSchId() {
		return schId;
	}
	public void setSchId(String schId) {
		this.schId = schId;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getFromClass() {
		return fromClass;
	}
	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}
	public String getFlowReason() {
		return flowReason;
	}
	public void setFlowReason(String flowReason) {
		this.flowReason = flowReason;
	}
	public Date getFlowDate() {
		return flowDate;
	}
	public void setFlowDate(Date flowDate) {
		this.flowDate = flowDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getToClass() {
		return toClass;
	}
	public void setToClass(String toClass) {
		this.toClass = toClass;
	}
}
