package net.zdsoft.office.salary.service;

import java.util.*;

import net.zdsoft.office.salary.entity.OfficeSalary;
import net.zdsoft.office.salary.entity.OfficeSalaryImport;
import net.zdsoft.office.salary.entity.OfficeSalarySort;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary
 * @author 
 * 
 */
public interface OfficeSalaryService{

	/**
	 * 新增office_salary
	 * @param officeSalary
	 * @return
	 */
	public OfficeSalary save(OfficeSalary officeSalary);

	/**
	 * 根据ids数组删除office_salary数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_salary
	 * @param officeSalary
	 * @return
	 */
	public Integer update(OfficeSalary officeSalary);

	/**
	 * 根据id获取office_salary
	 * @param id
	 * @return
	 */
	public OfficeSalary getOfficeSalaryById(String id);

	/**
	 * 根据ids数组查询office_salarymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSalary> getOfficeSalaryMapByIds(String[] ids);

	/**
	 * 获取office_salary列表
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryList();

	/**
	 * 分页获取office_salary列表
	 * @param page
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryPage(Pagination page);

	/**
	 * 根据UnitId获取office_salary列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_salary
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 批量插入或更新
	 * @param topicreportSubjInformationList
	 */
	public void batchInsertOrUpdate(List<OfficeSalary> officeSalary,OfficeSalaryImport officeSalaryImport,OfficeSalarySort officeSalarySort);
	
	/**
	 * 根据单位和项次id查询
	 * @param unitId
	 * @param importId
	 * @param page
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdAndImportId(String unitId,String importId,Pagination page);
	
	/**
	 * 根据单位和项次id和身份证进行查询
	 * @param unitId
	 * @param importId
	 * @param cardnumber
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdAndCardnumber(String unitId,String importId,String userId,String cardNumber,Pagination page);
}