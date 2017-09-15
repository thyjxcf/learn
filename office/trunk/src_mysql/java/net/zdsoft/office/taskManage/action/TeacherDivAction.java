package net.zdsoft.office.taskManage.action;

import java.util.List;

import net.zdsoft.eis.base.common.action.ObjectDivAction;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;

public class TeacherDivAction extends ObjectDivAction<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7320603687017736914L;
	
	private UserService userService;
	
	@Override
	public List<User> getDatasByGroupId() {
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
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
