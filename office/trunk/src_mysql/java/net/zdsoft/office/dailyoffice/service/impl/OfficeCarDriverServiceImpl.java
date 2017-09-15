package net.zdsoft.office.dailyoffice.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarDriverDao;
import net.zdsoft.office.dailyoffice.entity.OfficeCarDriver;
import net.zdsoft.office.dailyoffice.service.OfficeCarDriverService;
/**
 * office_car_driver
 * @author 
 * 
 */
public class OfficeCarDriverServiceImpl implements OfficeCarDriverService{
	private OfficeCarDriverDao officeCarDriverDao;

	@Override
	public OfficeCarDriver save(OfficeCarDriver officeCarDriver){
		return officeCarDriverDao.save(officeCarDriver);
	}
	
	@Override
	public void batchSave(List<OfficeCarDriver> officeCarDrivers){
		officeCarDriverDao.batchSave(officeCarDrivers);
	}
	
	@Override
	public void batchUpdate(List<OfficeCarDriver> officeCarDrivers){
		officeCarDriverDao.batchUpdate(officeCarDrivers);
	}

	@Override
	public Integer delete(String[] ids){
		return officeCarDriverDao.delete(ids);
	}

	@Override
	public Integer update(OfficeCarDriver officeCarDriver){
		return officeCarDriverDao.update(officeCarDriver);
	}

	@Override
	public OfficeCarDriver getOfficeCarDriverById(String id){
		return officeCarDriverDao.getOfficeCarDriverById(id);
	}

	@Override
	public Map<String, OfficeCarDriver> getOfficeCarDriverMapByIds(String[] ids){
		return officeCarDriverDao.getOfficeCarDriverMapByIds(ids);
	}
	
	@Override
	public Map<String, OfficeCarDriver> getOfficeCarDriverMapByUnitId(
			String unitId) {
		return officeCarDriverDao.getOfficeCarDriverMapByUnitId(unitId);
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverList(){
		return officeCarDriverDao.getOfficeCarDriverList();
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverPage(Pagination page){
		return officeCarDriverDao.getOfficeCarDriverPage(page);
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverByUnitIdList(String unitId){
		return officeCarDriverDao.getOfficeCarDriverByUnitIdList(unitId);
	}
	
	@Override
	public List<OfficeCarDriver> getOfficeCarDriverWithDelByUnitIdList(String unitId){
		return officeCarDriverDao.getOfficeCarDriverWithDelByUnitIdList(unitId);
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverByUnitIdPage(String unitId, Pagination page){
		return officeCarDriverDao.getOfficeCarDriverByUnitIdPage(unitId, page);
	}

	public void setOfficeCarDriverDao(OfficeCarDriverDao officeCarDriverDao){
		this.officeCarDriverDao = officeCarDriverDao;
	}
}