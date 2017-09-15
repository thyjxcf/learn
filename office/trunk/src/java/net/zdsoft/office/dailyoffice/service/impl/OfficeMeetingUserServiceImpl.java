package net.zdsoft.office.dailyoffice.service.impl;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeMeetingUser;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingUserService;
import net.zdsoft.office.dailyoffice.dao.OfficeMeetingUserDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_meeting_user
 * @author 
 * 
 */
public class OfficeMeetingUserServiceImpl implements OfficeMeetingUserService{
	private OfficeMeetingUserDao officeMeetingUserDao;

	@Override
	public OfficeMeetingUser save(OfficeMeetingUser officeMeetingUser){
		return officeMeetingUserDao.save(officeMeetingUser);
	}

	@Override
	public void batchsave(String applyId, String[] userIds){
		//通过申请ID删除原有的通知人员名单
		List<OfficeMeetingUser> userList = this.getOfficeMeetingUserListByApplyId(applyId);
		String[] oldUserIds = new String[userList.size()];
		for(int i = 0; i < userList.size(); i++){
			oldUserIds[i] = userList.get(i).getId();
		}
		this.delete(oldUserIds);
		//再将通知人员名单加入表中
		List<OfficeMeetingUser> meetingUserList = new ArrayList<OfficeMeetingUser>();
		for(int i = 0; i < userIds.length; i++){
			OfficeMeetingUser meetingUser = new OfficeMeetingUser();
			meetingUser.setMeetingApplyId(applyId);
			meetingUser.setUserId(userIds[i]);
			meetingUserList.add(meetingUser);
		}
		officeMeetingUserDao.batchsave(meetingUserList);
	}
	
	@Override
	public Integer delete(String[] ids){
		return officeMeetingUserDao.delete(ids);
	}

	@Override
	public Integer update(OfficeMeetingUser officeMeetingUser){
		return officeMeetingUserDao.update(officeMeetingUser);
	}

	@Override
	public OfficeMeetingUser getOfficeMeetingUserById(String id){
		return officeMeetingUserDao.getOfficeMeetingUserById(id);
	}

	@Override
	public Map<String, OfficeMeetingUser> getOfficeMeetingUserMapByIds(String[] ids){
		return officeMeetingUserDao.getOfficeMeetingUserMapByIds(ids);
	}

	@Override
	public List<OfficeMeetingUser> getOfficeMeetingUserList(){
		return officeMeetingUserDao.getOfficeMeetingUserList();
	}
	
	@Override
	public List<OfficeMeetingUser> getOfficeMeetingUserListByApplyId(String applyId){
		return officeMeetingUserDao.getOfficeMeetingUserListByApplyId(applyId);
	}

	@Override
	public List<OfficeMeetingUser> getOfficeMeetingUserPage(Pagination page){
		return officeMeetingUserDao.getOfficeMeetingUserPage(page);
	}

	public void setOfficeMeetingUserDao(OfficeMeetingUserDao officeMeetingUserDao){
		this.officeMeetingUserDao = officeMeetingUserDao;
	}
}
	