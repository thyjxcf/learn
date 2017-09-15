package net.zdsoft.eis.base.common.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

public class LoginLog extends BaseEntity {

	private static final long serialVersionUID = -7707871830170226378L;

	private String remoteIp;
	private String regionCode;
	private String unitId;
	private String accountId;
	private int userType;
	private int serverTypeId;
	private int serverId;
	private Date loginTime;

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
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

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
