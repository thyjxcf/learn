package net.zdsoft.office.teacherAttendance.service.impl;


import java.util.*;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceExcludeUserDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceExcludeUser;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceExcludeUserService;
/**
 * 不参与考勤统计人员信息
 * @author 
 * 
 */
public class OfficeAttendanceExcludeUserServiceImpl implements OfficeAttendanceExcludeUserService{
	private OfficeAttendanceExcludeUserDao officeAttendanceExcludeUserDao;

	@Override
	public OfficeAttendanceExcludeUser save(OfficeAttendanceExcludeUser officeAttendanceExcludeUser){
		return officeAttendanceExcludeUserDao.save(officeAttendanceExcludeUser);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendanceExcludeUserDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendanceExcludeUser officeAttendanceExcludeUser){
		return officeAttendanceExcludeUserDao.update(officeAttendanceExcludeUser);
	}

	@Override
	public OfficeAttendanceExcludeUser getOfficeAttendanceExcludeUserById(String id){
		return officeAttendanceExcludeUserDao.getOfficeAttendanceExcludeUserById(id);
	}

	@Override
	public List<OfficeAttendanceExcludeUser> getOfficeAttendanceExcludeUserByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		return officeAttendanceExcludeUserDao.getOfficeAttendanceExcludeUserByUnitId(unitId);
	}

	@Override
	public void save(String unitId, List<User> users) {
		// TODO Auto-generated method stub
		List<OfficeAttendanceExcludeUser> excludeUser = new ArrayList<OfficeAttendanceExcludeUser>();
		//先删除到不参加统计人员 然后在保存
		officeAttendanceExcludeUserDao.deleteByUnitId(unitId);
		for(User u:users){
			OfficeAttendanceExcludeUser user = new OfficeAttendanceExcludeUser();
			user.setUnitId(unitId);
			user.setUserId(u.getId());
			excludeUser.add(user);
		}
		officeAttendanceExcludeUserDao.batchSave(excludeUser);
	}

	public void setOfficeAttendanceExcludeUserDao(
			OfficeAttendanceExcludeUserDao officeAttendanceExcludeUserDao) {
		this.officeAttendanceExcludeUserDao = officeAttendanceExcludeUserDao;
	}

	@Override
	public Integer deleteByUserId(String[] ids) {
		// TODO Auto-generated method stub
		return officeAttendanceExcludeUserDao.deleteByUserId(ids);
	}
	
}