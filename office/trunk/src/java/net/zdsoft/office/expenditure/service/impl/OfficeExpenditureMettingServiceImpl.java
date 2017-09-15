package net.zdsoft.office.expenditure.service.impl;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureMetting;
import net.zdsoft.office.expenditure.service.OfficeExpenditureMettingService;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureMettingDao;
import net.zdsoft.keel.util.Pagination;
/**
 * 会议费
 * @author 
 * 
 */
public class OfficeExpenditureMettingServiceImpl implements OfficeExpenditureMettingService{
	private OfficeExpenditureMettingDao officeExpenditureMettingDao;

	@Override
	public OfficeExpenditureMetting save(OfficeExpenditureMetting officeExpenditureMetting){
		return officeExpenditureMettingDao.save(officeExpenditureMetting);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExpenditureMettingDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpenditureMetting officeExpenditureMetting){
		return officeExpenditureMettingDao.update(officeExpenditureMetting);
	}
	
	public Map<String, OfficeExpenditureMetting> getOfficeExpenditureMettingByExIds(
			String[] officeExpenditureIds){
		return officeExpenditureMettingDao.getOfficeExpenditureMettingByExIds(officeExpenditureIds);
	}

	@Override
	public OfficeExpenditureMetting getOfficeExpenditureMettingById(String id){
		return officeExpenditureMettingDao.getOfficeExpenditureMettingById(id);
	}

	@Override
	public OfficeExpenditureMetting getOfficeExpenditureMettingByPrimarId(
			String officeExpenditureId) {
		return officeExpenditureMettingDao.getOfficeExpenditureMettingByPrimarId(officeExpenditureId);
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		officeExpenditureMettingDao.deleteByExId(officeExpenditureId);
	}

	public void setOfficeExpenditureMettingDao(OfficeExpenditureMettingDao officeExpenditureMettingDao){
		this.officeExpenditureMettingDao = officeExpenditureMettingDao;
	}
}
