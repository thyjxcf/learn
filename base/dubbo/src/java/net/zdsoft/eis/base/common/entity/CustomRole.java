package net.zdsoft.eis.base.common.entity;

import java.util.List;

import net.zdsoft.eis.frame.client.BaseEntity;

public class CustomRole extends BaseEntity {

	private static final long serialVersionUID = -2453170131105666692L;

	private String unitId;

	private String roleName;

	private String roleCode;

	private String subsystems;

	private int orderId;

	private int type;

	private String remark;

	// 辅助字段
	private String userNames;

	private String userIds;
	
	private List<CustomRoleUser> users;
	
	private String unitName;
	
	private String order;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getSubsystems() {
		return subsystems;
	}

	public void setSubsystems(String subsystems) {
		this.subsystems = subsystems;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public List<CustomRoleUser> getUsers() {
		return users;
	}

	public void setUsers(List<CustomRoleUser> users) {
		this.users = users;
	}
	
	
}
