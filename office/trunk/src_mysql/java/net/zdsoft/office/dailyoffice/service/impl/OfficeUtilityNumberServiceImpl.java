package net.zdsoft.office.dailyoffice.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeUtilityNumberDao;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityNumber;
import net.zdsoft.office.dailyoffice.service.OfficeUtilityNumberService;
/**
 * office_utility_number
 * @author 
 * 
 */
public class OfficeUtilityNumberServiceImpl implements OfficeUtilityNumberService{
	private OfficeUtilityNumberDao officeUtilityNumberDao;

	@Override
	public void save(OfficeUtilityNumber officeUtilityNumber){
		officeUtilityNumberDao.save(officeUtilityNumber);
	}
	
	@Override
	public void addOfficeUtilityNumbers(
			List<OfficeUtilityNumber> officeUtilityNumbers) {
		officeUtilityNumberDao.insertOfficeUtilityNumbers(officeUtilityNumbers);
		
	}

	@Override
	public Integer delete(String[] ids){
		return officeUtilityNumberDao.delete(ids);
	}

	@Override
	public Integer update(OfficeUtilityNumber officeUtilityNumber){
		return officeUtilityNumberDao.update(officeUtilityNumber);
	}

	@Override
	public OfficeUtilityNumber getOfficeUtilityNumberById(String id){
		return officeUtilityNumberDao.getOfficeUtilityNumberById(id);
	}

	@Override
	public Map<String, OfficeUtilityNumber> getOfficeUtilityNumberMapByIds(String[] ids){
		return officeUtilityNumberDao.getOfficeUtilityNumberMapByIds(ids);
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberList(){
		return officeUtilityNumberDao.getOfficeUtilityNumberList();
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberPage(Pagination page){
		return officeUtilityNumberDao.getOfficeUtilityNumberPage(page);
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberByUnitIdList(String unitId){
		return officeUtilityNumberDao.getOfficeUtilityNumberByUnitIdList(unitId);
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberByUnitIdPage(String unitId, Pagination page){
		return officeUtilityNumberDao.getOfficeUtilityNumberByUnitIdPage(unitId, page);
	}
	
	@Override
	public String[] getUtilityApplyIds(String[] applyNumberIds) {
		return officeUtilityNumberDao.getUtilityApplyIds(applyNumberIds);
	}
	
	public void setOfficeUtilityNumberDao(OfficeUtilityNumberDao officeUtilityNumberDao){
		this.officeUtilityNumberDao = officeUtilityNumberDao;
	}
}	