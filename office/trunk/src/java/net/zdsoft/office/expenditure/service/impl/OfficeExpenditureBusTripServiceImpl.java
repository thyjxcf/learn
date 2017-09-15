package net.zdsoft.office.expenditure.service.impl;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureBusTrip;
import net.zdsoft.office.expenditure.service.OfficeExpenditureBusTripService;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureBusTripDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_bus_trip
 * @author 
 * 
 */
public class OfficeExpenditureBusTripServiceImpl implements OfficeExpenditureBusTripService{
	private OfficeExpenditureBusTripDao officeExpenditureBusTripDao;

	@Override
	public OfficeExpenditureBusTrip save(OfficeExpenditureBusTrip officeExpenditureBusTrip){
		return officeExpenditureBusTripDao.save(officeExpenditureBusTrip);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExpenditureBusTripDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpenditureBusTrip officeExpenditureBusTrip){
		return officeExpenditureBusTripDao.update(officeExpenditureBusTrip);
	}

	public Map<String, OfficeExpenditureBusTrip> getOfficeExpenditureBusTripByExIds(
			String[] officeExpenditureIds){
		return officeExpenditureBusTripDao.getOfficeExpenditureBusTripByExIds(officeExpenditureIds);
	}
	@Override
	public OfficeExpenditureBusTrip getOfficeExpenditureBusTripById(String id){
		return officeExpenditureBusTripDao.getOfficeExpenditureBusTripById(id);
	}

	@Override
	public OfficeExpenditureBusTrip getOfficeExpenditureBusTripByExId(
			String expenditureId) {
		return officeExpenditureBusTripDao.getOfficeExpenditureBusTripByExId(expenditureId);
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		officeExpenditureBusTripDao.deleteByExId(officeExpenditureId);
	}

	public void setOfficeExpenditureBusTripDao(OfficeExpenditureBusTripDao officeExpenditureBusTripDao){
		this.officeExpenditureBusTripDao = officeExpenditureBusTripDao;
	}
}
