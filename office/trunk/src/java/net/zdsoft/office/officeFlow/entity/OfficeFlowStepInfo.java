package net.zdsoft.office.officeFlow.entity;

import java.io.Serializable;
/**
 * office_flow_step_info
 * @author 
 * 
 */
public class OfficeFlowStepInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String flowId;
	/**
	 * 
	 */
	private String stepId;
	/**
	 * 
	 */
	private String taskUserId;
	/**
	 * 
	 */
	private String stepType;

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
	public void setStepId(String stepId){
		this.stepId = stepId;
	}
	/**
	 * 获取
	 */
	public String getStepId(){
		return this.stepId;
	}
	/**
	 * 设置
	 */
	public void setTaskUserId(String taskUserId){
		this.taskUserId = taskUserId;
	}
	/**
	 * 获取
	 */
	public String getTaskUserId(){
		return this.taskUserId;
	}
	/**
	 * 设置
	 */
	public void setStepType(String stepType){
		this.stepType = stepType;
	}
	/**
	 * 获取
	 */
	public String getStepType(){
		return this.stepType;
	}
}