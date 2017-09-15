package net.zdsoft.eis.system.frame.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

public class ModuleLog extends BaseEntity {
	private static final long serialVersionUID = -7707871830170226378L;

	private String moduleId;
	private String regionCode;
	private String unitId;
	private String accountId;
	private int userType;
	private int serverTypeId;
	private int serverId;
	private Date creationTime;
	private int subsystemId;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getServerTypeId() {
		return serverTypeId;
	}

	public void setServerTypeId(int serverTypeId) {
		this.serverTypeId = serverTypeId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(int subsystemId) {
		this.subsystemId = subsystemId;
	}

}
