package net.zdsoft.eis.base.data.sync.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winupon.syncdata.basedata.entity.son.MqUser;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.BatchHandlable;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

public class UserSyncServiceImpl extends AbstractHandlerTemplate<User, MqUser>
		implements BatchHandlable<User> {

	private BaseUserService baseUserService;

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	@Override
	public void addData(User e) throws BusinessErrorException {
		List<User> userList = new ArrayList<User>();
		userList.add(e);
		baseUserService.addUsersFromMq(userList);
	}

	@Override
	public void deleteData(String id, EventSourceType eventSource)
			throws BusinessErrorException {
		baseUserService.deleteUsers(new String[] { id }, eventSource);
	}

	@Override
	public User fetchOldEntity(String id) {
		return baseUserService.getUser(id);
	}

	@Override
	public void updateData(User e) throws BusinessErrorException {
		List<User> userList = new ArrayList<User>();
		userList.add(e);
		baseUserService.updateUsersFromMq(userList);
	}

	@Override
	public void addDatas(List<User> entities) throws BusinessErrorException {
		baseUserService.addUsersFromMq(entities);
	}

	@Override
	public void deleteDatas(String[] ids, EventSourceType eventSource)
			throws BusinessErrorException {
		baseUserService.deleteUsers(ids, eventSource);
	}

	@Override
	public Map<String, User> fetchOldEntities(String[] ids) {
		return baseUserService.getUsersMap(ids);
	}

	@Override
	public void updateDatas(List<User> entities) throws BusinessErrorException {
		baseUserService.updateUsersFromMq(entities);
	}

}
