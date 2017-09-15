package net.zdsoft.office.repaire.service.impl;

import java.util.*;

import net.zdsoft.office.repaire.entity.OfficeTeachAreaAuth;
import net.zdsoft.office.repaire.service.OfficeTeachAreaAuthService;
import net.zdsoft.office.repaire.dao.OfficeTeachAreaAuthDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_teach_area_auth
 * @author 
 * 
 */
public class OfficeTeachAreaAuthServiceImpl implements OfficeTeachAreaAuthService{
	private OfficeTeachAreaAuthDao officeTeachAreaAuthDao;

	@Override
	public OfficeTeachAreaAuth save(OfficeTeachAreaAuth officeTeachAreaAuth){
		return officeTeachAreaAuthDao.save(officeTeachAreaAuth);
	}

	@Override
	public Integer delete(String[] ids){
		return officeTeachAreaAuthDao.delete(ids);
	}

	@Override
	public Integer update(OfficeTeachAreaAuth officeTeachAreaAuth){
		return officeTeachAreaAuthDao.update(officeTeachAreaAuth);
	}

	@Override
	public OfficeTeachAreaAuth getOfficeTeachAreaAuthById(String id){
		return officeTeachAreaAuthDao.getOfficeTeachAreaAuthById(id);
	}

	@Override
	public Map<String, OfficeTeachAreaAuth> getOfficeTeachAreaAuthMapByIds(String[] ids){
		return officeTeachAreaAuthDao.getOfficeTeachAreaAuthMapByIds(ids);
	}

	@Override
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthList(){
		return officeTeachAreaAuthDao.getOfficeTeachAreaAuthList();
	}

	@Override
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthPage(Pagination page){
		return officeTeachAreaAuthDao.getOfficeTeachAreaAuthPage(page);
	}

	public void setOfficeTeachAreaAuthDao(OfficeTeachAreaAuthDao officeTeachAreaAuthDao){
		this.officeTeachAreaAuthDao = officeTeachAreaAuthDao;
	}
	@Override
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthList(String state,
			String userId) {
		return officeTeachAreaAuthDao.getOfficeTeachAreaAuthList(state,userId);
	}
	@Override
	public void deleteByUserIds(String[] userIds) {
		officeTeachAreaAuthDao.deleteByUserIds(userIds);
	}
	@Override
	public void saveBach(List<OfficeTeachAreaAuth> arealist) {
		// TODO Auto-generated method stub
		officeTeachAreaAuthDao.saveBach(arealist);
	}
}