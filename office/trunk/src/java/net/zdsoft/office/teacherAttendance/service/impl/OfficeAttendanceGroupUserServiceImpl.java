package net.zdsoft.office.teacherAttendance.service.impl;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceGroupUserDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;
/**
 * 考勤组成员
 * @author 
 * 
 */
public class OfficeAttendanceGroupUserServiceImpl implements OfficeAttendanceGroupUserService{
	private OfficeAttendanceGroupUserDao officeAttendanceGroupUserDao;

	@Override
	public OfficeAttendanceGroupUser save(OfficeAttendanceGroupUser officeAttendanceGroupUser){
		return officeAttendanceGroupUserDao.save(officeAttendanceGroupUser);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendanceGroupUserDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendanceGroupUser officeAttendanceGroupUser){
		return officeAttendanceGroupUserDao.update(officeAttendanceGroupUser);
	}

	@Override
	public OfficeAttendanceGroupUser getOfficeAttendanceGroupUserById(String id){
		return officeAttendanceGroupUserDao.getOfficeAttendanceGroupUserById(id);
	}
	
	public OfficeAttendanceGroupUser getItemByUserId(String userId){
		return officeAttendanceGroupUserDao.getItemByUserId(userId);
	}
	
	@Override
	public void batchSave(List<OfficeAttendanceGroupUser> groupUsers) {
		// TODO Auto-generated method stub
		officeAttendanceGroupUserDao.batchSave(groupUsers);
	}

	@Override
	public Integer deleteByGroupId(String groupId) {
		// TODO Auto-generated method stub
		return officeAttendanceGroupUserDao.deleteByGroupId(groupId);
	}

	public void setOfficeAttendanceGroupUserDao(
			OfficeAttendanceGroupUserDao officeAttendanceGroupUserDao) {
		this.officeAttendanceGroupUserDao = officeAttendanceGroupUserDao;
	}

	@Override
	public Map<String, List<OfficeAttendanceGroupUser>> getOfficeAttendanceGroupUserMap(String[] groupIds) {
		// TODO Auto-generated method stub
		List<OfficeAttendanceGroupUser> groupUserList = officeAttendanceGroupUserDao.getOfficeAttendanceGroupUser();
		 Map<String, List<OfficeAttendanceGroupUser>> map = new HashMap<String, List<OfficeAttendanceGroupUser>>();
		for(OfficeAttendanceGroupUser user:groupUserList){
			if(Arrays.asList(groupIds).contains(user.getGroupId())){
				List<OfficeAttendanceGroupUser> list = map.get(user.getGroupId());
				if(list == null){
					list =  new ArrayList<OfficeAttendanceGroupUser>();
					list.add(user);
					map.put(user.getGroupId(), list);
				}else{
					list.add(user);
				}
			}
			
			
		}
		return map;
	}
	@Override
	public Map<String,String> getGroupUser(String[] userIds){
		return officeAttendanceGroupUserDao.getGroupUser(userIds); 
	}
	@Override
	public Integer deleteByGroupIdAndUserId(String groupId, String userId) {
		// TODO Auto-generated method stub
		return officeAttendanceGroupUserDao.deleteByGroupIdAndUserId(groupId, userId);
	}

	@Override
	public List<OfficeAttendanceGroupUser> getOfficeAttendanceGroupUser() {
		// TODO Auto-generated method stub
		return officeAttendanceGroupUserDao.getOfficeAttendanceGroupUser();
	}
	
}