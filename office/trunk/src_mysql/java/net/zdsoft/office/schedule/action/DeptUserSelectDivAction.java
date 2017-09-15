package net.zdsoft.office.schedule.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.action.ObjectDivAction;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;

public class DeptUserSelectDivAction  extends ObjectDivAction<User>{
	private static final long serialVersionUID = -6240783247698646532L;
	
	private UserService userService;
	private String deptId;
	
	@Override
	protected List<User> getDatasByConditon() {
		if(StringUtils.isNotBlank(deptId))
			return userService.getUsersByDeptId(deptId);
		else
			return userService.getUsers(getUnitId());
	}
	
	@Override
	protected void toObject(User user, SimpleObject object) {
		if (user == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(user.getId());
		object.setObjectName(user.getRealname());
		object.setUnitId(user.getUnitid());
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
