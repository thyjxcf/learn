package net.zdsoft.office.salary.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.salary.dao.OfficeSalaryDao;
import net.zdsoft.office.salary.dao.OfficeSalaryImportDao;
import net.zdsoft.office.salary.dao.OfficeSalarySortDao;
import net.zdsoft.office.salary.entity.OfficeSalary;
import net.zdsoft.office.salary.entity.OfficeSalaryImport;
import net.zdsoft.office.salary.entity.OfficeSalarySort;
import net.zdsoft.office.salary.service.OfficeSalaryService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_salary
 * @author 
 * 
 */
public class OfficeSalaryServiceImpl implements OfficeSalaryService{
	private OfficeSalaryDao officeSalaryDao;
	private OfficeSalaryImportDao officeSalaryImportDao;
	private OfficeSalarySortDao officeSalarySortDao;

	@Override
	public OfficeSalary save(OfficeSalary officeSalary){
		return officeSalaryDao.save(officeSalary);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSalaryDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSalary officeSalary){
		return officeSalaryDao.update(officeSalary);
	}

	@Override
	public OfficeSalary getOfficeSalaryById(String id){
		return officeSalaryDao.getOfficeSalaryById(id);
	}

	@Override
	public Map<String, OfficeSalary> getOfficeSalaryMapByIds(String[] ids){
		return officeSalaryDao.getOfficeSalaryMapByIds(ids);
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryList(){
		return officeSalaryDao.getOfficeSalaryList();
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryPage(Pagination page){
		return officeSalaryDao.getOfficeSalaryPage(page);
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdList(String unitId){
		return officeSalaryDao.getOfficeSalaryByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdPage(String unitId, Pagination page){
		return officeSalaryDao.getOfficeSalaryByUnitIdPage(unitId, page);
	}

	public void setOfficeSalaryDao(OfficeSalaryDao officeSalaryDao){
		this.officeSalaryDao = officeSalaryDao;
	}

	public void setOfficeSalaryImportDao(OfficeSalaryImportDao officeSalaryImportDao) {
		this.officeSalaryImportDao = officeSalaryImportDao;
	}

	@Override
	public void batchInsertOrUpdate(List<OfficeSalary> officeSalary,
			OfficeSalaryImport officeSalaryImport,OfficeSalarySort officeSalarySort) {
		OfficeSalaryImport officeSalaryImpor=officeSalaryImportDao.getOfficeSalaryImportByUnitExcelName(officeSalaryImport.getUnitId(), officeSalaryImport.getMonthtime());
		List<OfficeSalary> officeSalarys=null;
		if(officeSalaryImpor!=null&&StringUtils.isNotBlank(officeSalaryImpor.getId())){
			
			officeSalarys=officeSalaryDao.getOfficeSalaryByUnitIdId(officeSalaryImpor.getUnitId(), officeSalaryImpor.getId(),null);
		}
		Set<String> importId=new HashSet<String>();
		if(CollectionUtils.isNotEmpty(officeSalarys)){
			for (OfficeSalary officeSal : officeSalarys) {
				importId.add(officeSal.getId());
			}
		}
		if(officeSalaryImpor!=null){
			officeSalaryImportDao.delete(new String[]{officeSalaryImpor.getId()});
			officeSalaryDao.delete(importId.toArray(new String[0]));
			officeSalarySortDao.delete(new String[]{officeSalaryImpor.getId()});
		}
		officeSalaryImportDao.save(officeSalaryImport);
		officeSalaryDao.batchInsert(officeSalary);
		if(officeSalarySort!=null){
			officeSalarySort.setImportId(officeSalaryImport.getId());
			officeSalarySortDao.save(officeSalarySort);
		}
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdAndImportId(String unitId,
			String importId, Pagination page) {
		return officeSalaryDao.getOfficeSalaryByUnitIdId(unitId, importId, page);
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdAndCardnumber(
			String unitId, String importId,String userId,String cardNumber,Pagination page) {
		return officeSalaryDao.getOfficeSalaryByUnitIdAndCardnumber(unitId, importId, userId,cardNumber,page);
	}

	public void setOfficeSalarySortDao(OfficeSalarySortDao officeSalarySortDao) {
		this.officeSalarySortDao = officeSalarySortDao;
	}
	
}
