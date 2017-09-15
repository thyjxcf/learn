package net.zdsoft.eis.system.frame.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class FlowDiagramDetail extends BaseEntity{

	private static final long serialVersionUID = -5503238387205486842L;

	private String tfdId;// 流程主表id
	private int modelId;// 模块表id
	private String parentId;// 流程中的父节点id，第一个节点，此值为32个0
	private int platform;//平台
	private int orderId;
	private int subsystem;
	private int unitClass;
	private String modelName;
	private boolean permission;
	
	
	public String getTfdId() {
		return tfdId;
	}

	public void setTfdId(String tfdId) {
		this.tfdId = tfdId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(int subsystem) {
		this.subsystem = subsystem;
	}

	public int getUnitClass() {
		return unitClass;
	}

	public void setUnitClass(int unitClass) {
		this.unitClass = unitClass;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

}
