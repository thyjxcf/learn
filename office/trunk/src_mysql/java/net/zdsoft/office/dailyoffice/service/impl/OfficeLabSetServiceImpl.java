package net.zdsoft.office.dailyoffice.service.impl;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeLabSet;
import net.zdsoft.office.dailyoffice.service.OfficeLabSetService;
import net.zdsoft.office.dailyoffice.dao.OfficeLabSetDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_lab_set
 * @author 
 * 
 */
public class OfficeLabSetServiceImpl implements OfficeLabSetService{
	private OfficeLabSetDao officeLabSetDao;

	@Override
	public OfficeLabSet save(OfficeLabSet officeLabSet){
		return officeLabSetDao.save(officeLabSet);
	}

	@Override
	public Integer delete(String[] ids){
		return officeLabSetDao.delete(ids);
	}

	@Override
	public Integer update(OfficeLabSet officeLabSet){
		return officeLabSetDao.update(officeLabSet);
	}

	@Override
	public OfficeLabSet getOfficeLabSetById(String id){
		return officeLabSetDao.getOfficeLabSetById(id);
	}

	@Override
	public Map<String, OfficeLabSet> getOfficeLabSetMapByIds(String[] ids){
		return officeLabSetDao.getOfficeLabSetMapByIds(ids);
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetList(){
		return officeLabSetDao.getOfficeLabSetList();
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetPage(Pagination page){
		return officeLabSetDao.getOfficeLabSetPage(page);
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetByUnitIdList(String unitId){
		return officeLabSetDao.getOfficeLabSetByUnitIdList(unitId);
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetByUnitIdPage(String unitId, Pagination page){
		return officeLabSetDao.getOfficeLabSetByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeLabSet> getOfficeLabSetBySubjectPage(String unitId, String subject, String grade, Pagination page){
		return officeLabSetDao.getOfficeLabSetBySubjectPage(unitId, subject, grade, page);
	}
	
	@Override
	public Map<String, OfficeLabSet> getOfficeLabSetMapByConditions(String unitId, String searchName, String searchSubject, String searchGrade){
		return officeLabSetDao.getOfficeLabSetMapByConditions(unitId, searchName, searchSubject, searchGrade);
	}

	public void setOfficeLabSetDao(OfficeLabSetDao officeLabSetDao){
		this.officeLabSetDao = officeLabSetDao;
	}
}
	