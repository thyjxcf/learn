package net.zdsoft.office.salary.service;

import java.util.*;
import net.zdsoft.office.salary.entity.OfficeSalaryImport;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary_import
 * @author 
 * 
 */
public interface OfficeSalaryImportService{

	/**
	 * 新增office_salary_import
	 * @param officeSalaryImport
	 * @return
	 */
	public OfficeSalaryImport save(OfficeSalaryImport officeSalaryImport);

	/**
	 * 根据ids数组删除office_salary_import数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_salary_import
	 * @param officeSalaryImport
	 * @return
	 */
	public Integer update(OfficeSalaryImport officeSalaryImport);

	/**
	 * 根据id获取office_salary_import
	 * @param id
	 * @return
	 */
	public OfficeSalaryImport getOfficeSalaryImportById(String id);

	/**
	 * 根据ids数组查询office_salary_importmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSalaryImport> getOfficeSalaryImportMapByIds(String[] ids);

	/**
	 * 获取office_salary_import列表
	 * @return
	 */
	public List<OfficeSalaryImport> getOfficeSalaryImportList();

	/**
	 * 分页获取office_salary_import列表
	 * @param page
	 * @return
	 */
	public List<OfficeSalaryImport> getOfficeSalaryImportPage(Pagination page);

	/**
	 * 根据UnitId获取office_salary_import列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_salary_import
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据时间取最早的记录
	 * @param unitId
	 * @return
	 */
	public OfficeSalaryImport getOfficeSalaryImportByTime(String unitId,String salaryTime);
	
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdAndTime(String unitId,String salaryTime);
	
	public void deleteAll(String unitId,String[] ids);
}