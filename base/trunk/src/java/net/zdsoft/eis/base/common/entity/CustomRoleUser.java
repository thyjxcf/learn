package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class CustomRoleUser extends BaseEntity{

	private static final long serialVersionUID = -7364382830500732873L;
	
	private String roleId;
	
	private String userId;

	private String userName;
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
