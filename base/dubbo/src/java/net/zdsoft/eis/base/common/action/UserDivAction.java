package net.zdsoft.eis.base.common.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;

public class UserDivAction extends ObjectDivAction<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256754851851305562L;

	private UserService userService;
	
	public UserDivAction() {
		setShowLetterIndex(true);
	}
	
	@Override
	protected List<User> getDatasByUnitId() {
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

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
