package net.zdsoft.office.repaire.service.impl;

import java.util.*;

import net.zdsoft.office.repaire.entity.OfficeRepaireSms;
import net.zdsoft.office.repaire.service.OfficeRepaireSmsService;
import net.zdsoft.office.repaire.dao.OfficeRepaireSmsDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_repaire_sms
 * @author 
 * 
 */
public class OfficeRepaireSmsServiceImpl implements OfficeRepaireSmsService{
	private OfficeRepaireSmsDao officeRepaireSmsDao;

	@Override
	public OfficeRepaireSms save(OfficeRepaireSms officeRepaireSms){
		return officeRepaireSmsDao.save(officeRepaireSms);
	}

	@Override
	public Integer delete(String[] ids){
		return officeRepaireSmsDao.delete(ids);
	}

	@Override
	public Integer update(OfficeRepaireSms officeRepaireSms){
		return officeRepaireSmsDao.update(officeRepaireSms);
	}

	@Override
	public OfficeRepaireSms getOfficeRepaireSmsById(String id){
		return officeRepaireSmsDao.getOfficeRepaireSmsById(id);
	}

	@Override
	public Map<String, OfficeRepaireSms> getOfficeRepaireSmsMapByIds(String[] ids){
		return officeRepaireSmsDao.getOfficeRepaireSmsMapByIds(ids);
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsList(){
		return officeRepaireSmsDao.getOfficeRepaireSmsList();
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsPage(Pagination page){
		return officeRepaireSmsDao.getOfficeRepaireSmsPage(page);
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdList(String unitId){
		return officeRepaireSmsDao.getOfficeRepaireSmsByUnitIdList(unitId);
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdPage(String unitId, Pagination page){
		return officeRepaireSmsDao.getOfficeRepaireSmsByUnitIdPage(unitId, page);
	}
	
	@Override
	public void batchInsert(List<OfficeRepaireSms> insertList){
		officeRepaireSmsDao.batchInsert(insertList);
	}
	
	@Override
	public OfficeRepaireSms getOfficeRepaireSmsByTypeId(String unitId, String typeId){
		List<OfficeRepaireSms> list = officeRepaireSmsDao.getOfficeRepaireSmsByTypeId(unitId, typeId);
		if(list.size() > 0)
			return list.get(0);
		else
			return new OfficeRepaireSms();
	}
	
	public void batchUpdate(List<OfficeRepaireSms> updateList){
		officeRepaireSmsDao.batchUpdate(updateList);
	}

	public void setOfficeRepaireSmsDao(OfficeRepaireSmsDao officeRepaireSmsDao){
		this.officeRepaireSmsDao = officeRepaireSmsDao;
	}
}
