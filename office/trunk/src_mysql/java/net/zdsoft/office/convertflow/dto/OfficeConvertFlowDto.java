package net.zdsoft.office.convertflow.dto;

public class OfficeConvertFlowDto {
	
	private String unitId;//
	
	private String businessId;//业务表id
	
	private Integer type;//数据类型，请假、外出、物品等
	
	private String curUserId;//当前步骤审核人--工作流相关模块有用到
	
	private String applyUserId;//申请人id
	
	private String flowId;//工作流flowId
	
	private Integer status;//审核状态
	
	private String parm;
	
	private String auditParm;//审核参数---下一步审核人id、物品id或者角色等 
	

	public String getCurUserId() {
		return curUserId;
	}

	public void setCurUserId(String curUserId) {
		this.curUserId = curUserId;
	}

	public String getAuditParm() {
		return auditParm;
	}

	public void setAuditParm(String auditParm) {
		this.auditParm = auditParm;
	}

	public String getParm() {
		return parm;
	}

	public void setParm(String parm) {
		this.parm = parm;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	
}
