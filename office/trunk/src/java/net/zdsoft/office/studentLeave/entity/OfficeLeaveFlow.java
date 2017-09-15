package net.zdsoft.office.studentLeave.entity;

import java.io.Serializable;
/**
 * office_leave_flow
 * @author 
 * 
 */
public class OfficeLeaveFlow implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	private String unitId;
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	/**
	 * 
	 */
	private String leaveType;
	/**
	 * 
	 */
	private String flowId;
	
	private String flowIds;//
	
	private String flowName;
	
	private String gradeId;
	private String gradeName;
	
	private Integer section;
	
	private String acadyear;

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
	public void setLeaveType(String leaveType){
		this.leaveType = leaveType;
	}
	/**
	 * 获取
	 */
	public String getLeaveType(){
		return this.leaveType;
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
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getFlowIds() {
		return flowIds;
	}
	public void setFlowIds(String flowIds) {
		this.flowIds = flowIds;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getSection() {
		return section;
	}
	public void setSection(Integer section) {
		this.section = section;
	}
	public String getAcadyear() {
		return acadyear;
	}
	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
}