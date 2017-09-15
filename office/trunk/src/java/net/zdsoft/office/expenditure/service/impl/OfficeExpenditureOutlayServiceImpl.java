package net.zdsoft.office.expenditure.service.impl;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureOutlay;
import net.zdsoft.office.expenditure.service.OfficeExpenditureOutlayService;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureOutlayDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_outlay
 * @author 
 * 
 */
public class OfficeExpenditureOutlayServiceImpl implements OfficeExpenditureOutlayService{
	private OfficeExpenditureOutlayDao officeExpenditureOutlayDao;

	@Override
	public OfficeExpenditureOutlay save(OfficeExpenditureOutlay officeExpenditureOutlay){
		return officeExpenditureOutlayDao.save(officeExpenditureOutlay);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExpenditureOutlayDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpenditureOutlay officeExpenditureOutlay){
		return officeExpenditureOutlayDao.update(officeExpenditureOutlay);
	}
	
	public Map<String, OfficeExpenditureOutlay> getOfficeExpenditureOutlayByExIds(
			String[] officeExpenditureIds){
		return officeExpenditureOutlayDao.getOfficeExpenditureOutlayByExIds(officeExpenditureIds);
	}

	@Override
	public OfficeExpenditureOutlay getOfficeExpenditureOutlayById(String id){
		return officeExpenditureOutlayDao.getOfficeExpenditureOutlayById(id);
	}

	@Override
	public OfficeExpenditureOutlay getOfficeExpenditureOutlayByExId(
			String expenditureId) {
		return officeExpenditureOutlayDao.getOfficeExpenditureOutlayByExId(expenditureId);
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		officeExpenditureOutlayDao.deleteByExId(officeExpenditureId);
	}

	public void setOfficeExpenditureOutlayDao(OfficeExpenditureOutlayDao officeExpenditureOutlayDao){
		this.officeExpenditureOutlayDao = officeExpenditureOutlayDao;
	}
}
	