package net.zdsoft.eis.system.data.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.data.service.BaseCustomRoleUserService;

public class CustomRoleAction extends BaseAction {

	private static final long serialVersionUID = -5496318809247775326L;

	private CustomRoleService customRoleService;

	private BaseCustomRoleUserService baseCustomRoleUserService;

	private List<CustomRole> roleList = new ArrayList<CustomRole>();

	private String roleId;

	private String subsystemId;// 子系统id

	private String userIds;

	public String execute() {
		roleList = customRoleService.getCustomRoleWithUserList(getUnitId(),
				subsystemId);
		return SUCCESS;
	}

	public String save() {
		try {
			String[] selectedUserIds =null;
			if(StringUtils.isNotBlank(userIds))
				selectedUserIds = userIds.split(",");
			baseCustomRoleUserService.saveCustomRoleUsers(roleId,
					selectedUserIds);
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
		}
		return "message";
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public List<CustomRole> getRoleList() {
		return roleList;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setBaseCustomRoleUserService(
			BaseCustomRoleUserService baseCustomRoleUserService) {
		this.baseCustomRoleUserService = baseCustomRoleUserService;
	}

}
