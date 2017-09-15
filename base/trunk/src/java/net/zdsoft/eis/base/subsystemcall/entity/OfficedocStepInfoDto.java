package net.zdsoft.eis.base.subsystemcall.entity;

public class OfficedocStepInfoDto {
	private String stepId;
	
	private String nextStepId;
	
	private String stepUser;
	
	private String stepUserName;
	
	private String stepUserDetailName;
	
	private int stepUserType;
	
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getNextStepId() {
		return nextStepId;
	}
	public void setNextStepId(String nextStepId) {
		this.nextStepId = nextStepId;
	}
	public String getStepUser() {
		return stepUser;
	}
	public void setStepUser(String stepUser) {
		this.stepUser = stepUser;
	}
	
	public String getStepUserName() {
		return stepUserName;
	}
	public void setStepUserName(String stepUserName) {
		this.stepUserName = stepUserName;
	}
	
	public String getStepUserDetailName() {
		return stepUserDetailName;
	}
	public void setStepUserDetailName(String stepUserDetailName) {
		this.stepUserDetailName = stepUserDetailName;
	}
	
	public void setStepUserType(int stepUserType) {
		this.stepUserType = stepUserType;
	}
	
	public int getStepUserType() {
		return stepUserType;
	}
	
	public OfficedocStepInfoDto(String stepId,String nextStepId,String stepUser,String stepUserName,String taskDetailName,int stepUserType) {
		this.stepId= stepId;
		this.nextStepId = nextStepId;
		this.stepUser = stepUser;
		this.stepUserName =stepUserName;
		this.stepUserDetailName = taskDetailName;
		this.stepUserType = stepUserType;
	}
}
