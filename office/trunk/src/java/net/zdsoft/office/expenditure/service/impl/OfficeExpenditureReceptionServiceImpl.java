package net.zdsoft.office.expenditure.service.impl;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureReception;
import net.zdsoft.office.expenditure.service.OfficeExpenditureReceptionService;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureReceptionDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_reception
 * @author 
 * 
 */
public class OfficeExpenditureReceptionServiceImpl implements OfficeExpenditureReceptionService{
	private OfficeExpenditureReceptionDao officeExpenditureReceptionDao;

	@Override
	public OfficeExpenditureReception save(OfficeExpenditureReception officeExpenditureReception){
		return officeExpenditureReceptionDao.save(officeExpenditureReception);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExpenditureReceptionDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpenditureReception officeExpenditureReception){
		return officeExpenditureReceptionDao.update(officeExpenditureReception);
	}

	public Map<String, OfficeExpenditureReception> getOfficeExpenditureReceptionByExIds(
			String[] officeExpenditureIds){
		return officeExpenditureReceptionDao.getOfficeExpenditureReceptionByExIds(officeExpenditureIds);
	}

	@Override
	public OfficeExpenditureReception getOfficeExpenditureReceptionById(String id){
		return officeExpenditureReceptionDao.getOfficeExpenditureReceptionById(id);
	}

	@Override
	public OfficeExpenditureReception getOfficeExpenditureReceptionByExId(
			String officeExpenditureId) {
		return officeExpenditureReceptionDao.getOfficeExpenditureReceptionByExId(officeExpenditureId);
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		officeExpenditureReceptionDao.deleteByExId(officeExpenditureId);
	}

	public void setOfficeExpenditureReceptionDao(OfficeExpenditureReceptionDao officeExpenditureReceptionDao){
		this.officeExpenditureReceptionDao = officeExpenditureReceptionDao;
	}
}
