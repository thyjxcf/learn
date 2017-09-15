package net.zdsoft.office.salary.service.impl;

import java.util.*;
import net.zdsoft.office.salary.entity.OfficeSalarySort;
import net.zdsoft.office.salary.service.OfficeSalarySortService;
import net.zdsoft.office.salary.dao.OfficeSalarySortDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary_sort
 * @author 
 * 
 */
public class OfficeSalarySortServiceImpl implements OfficeSalarySortService{
	private OfficeSalarySortDao officeSalarySortDao;

	@Override
	public OfficeSalarySort save(OfficeSalarySort officeSalarySort){
		return officeSalarySortDao.save(officeSalarySort);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSalarySortDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSalarySort officeSalarySort){
		return officeSalarySortDao.update(officeSalarySort);
	}

	@Override
	public OfficeSalarySort getOfficeSalarySortById(String id){
		return officeSalarySortDao.getOfficeSalarySortById(id);
	}

	@Override
	public Map<String, OfficeSalarySort> getOfficeSalarySortMapByIds(String[] ids){
		return officeSalarySortDao.getOfficeSalarySortMapByIds(ids);
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortList(){
		return officeSalarySortDao.getOfficeSalarySortList();
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortPage(Pagination page){
		return officeSalarySortDao.getOfficeSalarySortPage(page);
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortByUnitIdList(String unitId){
		return officeSalarySortDao.getOfficeSalarySortByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortByUnitIdPage(String unitId, Pagination page){
		return officeSalarySortDao.getOfficeSalarySortByUnitIdPage(unitId, page);
	}

	@Override
	public OfficeSalarySort getOfficeSalarySortByImportId(String importId) {
		return officeSalarySortDao.getOfficeSalarySortByImportId(importId);
	}

	public void setOfficeSalarySortDao(OfficeSalarySortDao officeSalarySortDao){
		this.officeSalarySortDao = officeSalarySortDao;
	}
}
