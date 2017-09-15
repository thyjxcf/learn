package net.zdsoft.eis.base.subsystemcall.entity;

public class OfficeStepInfoDto {
	private String flowId;
	private String stepId;
	private String taskUserId;
	private String taskUserName;
	private String taskDetailName;
	private String stepType;
	
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getTaskUserId() {
		return taskUserId;
	}
	public void setTaskUserId(String taskUserId) {
		this.taskUserId = taskUserId;
	}
	public String getTaskUserName() {
		return taskUserName;
	}
	public void setTaskUserName(String taskUserName) {
		this.taskUserName = taskUserName;
	}
	public String getStepType() {
		return stepType;
	}
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}
	public String getTaskDetailName() {
		return taskDetailName;
	}
	public void setTaskDetailName(String taskDetailName) {
		this.taskDetailName = taskDetailName;
	}
	
}
