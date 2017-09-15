package net.zdsoft.office.teacherLeave.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherLeave.dao.OfficeTeacherLeaveUserDao;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveUser;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveUserService;
/**
 * office_teacher_leave_user
 * @author 
 * 
 */
public class OfficeTeacherLeaveUserServiceImpl implements OfficeTeacherLeaveUserService{
	private OfficeTeacherLeaveUserDao officeTeacherLeaveUserDao;

	@Override
	public OfficeTeacherLeaveUser save(OfficeTeacherLeaveUser officeTeacherLeaveUser){
		return officeTeacherLeaveUserDao.save(officeTeacherLeaveUser);
	}
	
	@Override
	public void batchSave(
			List<OfficeTeacherLeaveUser> officeTeacherLeaveUsers) {
		officeTeacherLeaveUserDao.batchSave(officeTeacherLeaveUsers);
	}

	@Override
	public Integer deleteByLeaveIds(String[] ids){
		return officeTeacherLeaveUserDao.deleteByLeaveIds(ids);
	}
	
	@Override
	public void deleteByLeaveId(String leaveId) {
		officeTeacherLeaveUserDao.deleteByLeaveId(leaveId);
	}

	@Override
	public Integer update(OfficeTeacherLeaveUser officeTeacherLeaveUser){
		return officeTeacherLeaveUserDao.update(officeTeacherLeaveUser);
	}

	@Override
	public OfficeTeacherLeaveUser getOfficeTeacherLeaveUserById(String id){
		return officeTeacherLeaveUserDao.getOfficeTeacherLeaveUserById(id);
	}

	@Override
	public Map<String, OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserMapByIds(String[] ids){
		return officeTeacherLeaveUserDao.getOfficeTeacherLeaveUserMapByIds(ids);
	}

	@Override
	public List<OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserList(String leaveId){
		return officeTeacherLeaveUserDao.getOfficeTeacherLeaveUserList(leaveId);
	}

	@Override
	public List<OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserPage(Pagination page){
		return officeTeacherLeaveUserDao.getOfficeTeacherLeaveUserPage(page);
	}

	public void setOfficeTeacherLeaveUserDao(OfficeTeacherLeaveUserDao officeTeacherLeaveUserDao){
		this.officeTeacherLeaveUserDao = officeTeacherLeaveUserDao;
	}
}