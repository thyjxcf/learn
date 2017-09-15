package net.zdsoft.office.salary.service.impl;

import java.util.*;

import net.zdsoft.office.salary.entity.OfficeSalary;
import net.zdsoft.office.salary.entity.OfficeSalaryImport;
import net.zdsoft.office.salary.service.OfficeSalaryImportService;
import net.zdsoft.office.salary.dao.OfficeSalaryDao;
import net.zdsoft.office.salary.dao.OfficeSalaryImportDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary_import
 * @author 
 * 
 */
public class OfficeSalaryImportServiceImpl implements OfficeSalaryImportService{
	private OfficeSalaryImportDao officeSalaryImportDao;
	private OfficeSalaryDao officeSalaryDao;

	@Override
	public OfficeSalaryImport save(OfficeSalaryImport officeSalaryImport){
		return officeSalaryImportDao.save(officeSalaryImport);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSalaryImportDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSalaryImport officeSalaryImport){
		return officeSalaryImportDao.update(officeSalaryImport);
	}

	@Override
	public OfficeSalaryImport getOfficeSalaryImportById(String id){
		return officeSalaryImportDao.getOfficeSalaryImportById(id);
	}

	@Override
	public Map<String, OfficeSalaryImport> getOfficeSalaryImportMapByIds(String[] ids){
		return officeSalaryImportDao.getOfficeSalaryImportMapByIds(ids);
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportList(){
		return officeSalaryImportDao.getOfficeSalaryImportList();
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportPage(Pagination page){
		return officeSalaryImportDao.getOfficeSalaryImportPage(page);
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdList(String unitId){
		return officeSalaryImportDao.getOfficeSalaryImportByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdPage(String unitId, Pagination page){
		return officeSalaryImportDao.getOfficeSalaryImportByUnitIdPage(unitId, page);
	}

	@Override
	public OfficeSalaryImport getOfficeSalaryImportByTime(String unitId,String salaryTime) {
		return officeSalaryImportDao.getOfficeSalaryImportByTime(unitId,salaryTime);
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdAndTime(
			String unitId, String salaryTime) {
		return officeSalaryImportDao.getOfficeSalaryImportByUnitIdAndTime(unitId, salaryTime);
	}

	@Override
	public void deleteAll(String unitId, String[] ids) {
		List<OfficeSalary> officeSalarys=officeSalaryDao.getOfficeSalaryListByImportId(unitId, ids);
		Set<String> officeSalaryIdSet=new HashSet<String>();
		for (OfficeSalary officeSalary : officeSalarys) {
			officeSalaryIdSet.add(officeSalary.getId());
		}
		officeSalaryImportDao.delete(ids);
		officeSalaryDao.delete(officeSalaryIdSet.toArray(new String[0]));
	}

	public void setOfficeSalaryImportDao(OfficeSalaryImportDao officeSalaryImportDao){
		this.officeSalaryImportDao = officeSalaryImportDao;
	}

	public void setOfficeSalaryDao(OfficeSalaryDao officeSalaryDao) {
		this.officeSalaryDao = officeSalaryDao;
	}
	
}
