package net.zdsoft.office.meeting.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveMinutesUserDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMinutesUser;
import net.zdsoft.office.meeting.service.OfficeExecutiveMinutesUserService;
/**
 * office_executive_minutes_user
 * @author 
 * 
 */
public class OfficeExecutiveMinutesUserServiceImpl implements OfficeExecutiveMinutesUserService{
	private OfficeExecutiveMinutesUserDao officeExecutiveMinutesUserDao;

	@Override
	public OfficeExecutiveMinutesUser save(OfficeExecutiveMinutesUser officeExecutiveMinutesUser){
		return officeExecutiveMinutesUserDao.save(officeExecutiveMinutesUser);
	}
	
	@Override
	public void batchUpdate(String unitId, String userIds) {
		String[] userIds_ = userIds.split(",");
		List<OfficeExecutiveMinutesUser> list = new ArrayList<OfficeExecutiveMinutesUser>();
		for(String userId:userIds_){
			OfficeExecutiveMinutesUser oemu = new OfficeExecutiveMinutesUser();
			oemu.setUnitId(unitId);
			oemu.setUserId(userId);
			list.add(oemu);
		}
		officeExecutiveMinutesUserDao.deleteByUnitId(unitId);
		officeExecutiveMinutesUserDao.batchSave(list);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExecutiveMinutesUserDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExecutiveMinutesUser officeExecutiveMinutesUser){
		return officeExecutiveMinutesUserDao.update(officeExecutiveMinutesUser);
	}

	@Override
	public OfficeExecutiveMinutesUser getOfficeExecutiveMinutesUserById(String id){
		return officeExecutiveMinutesUserDao.getOfficeExecutiveMinutesUserById(id);
	}

	@Override
	public Map<String, OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserMapByIds(String[] ids){
		return officeExecutiveMinutesUserDao.getOfficeExecutiveMinutesUserMapByIds(ids);
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserList(){
		return officeExecutiveMinutesUserDao.getOfficeExecutiveMinutesUserList();
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserPage(Pagination page){
		return officeExecutiveMinutesUserDao.getOfficeExecutiveMinutesUserPage(page);
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserByUnitIdList(String unitId){
		return officeExecutiveMinutesUserDao.getOfficeExecutiveMinutesUserByUnitIdList(unitId);
	}

	@Override
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserByUnitIdPage(String unitId, Pagination page){
		return officeExecutiveMinutesUserDao.getOfficeExecutiveMinutesUserByUnitIdPage(unitId, page);
	}
	
	@Override
	public boolean isMinutesUser(String unitId, String userId) {
		return officeExecutiveMinutesUserDao.isMinutesUser(unitId,userId);
	}

	public void setOfficeExecutiveMinutesUserDao(OfficeExecutiveMinutesUserDao officeExecutiveMinutesUserDao){
		this.officeExecutiveMinutesUserDao = officeExecutiveMinutesUserDao;
	}
}