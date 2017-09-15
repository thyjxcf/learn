package net.zdsoft.office.salary.service;

import java.util.*;
import net.zdsoft.office.salary.entity.OfficeSalarySort;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary_sort
 * @author 
 * 
 */
public interface OfficeSalarySortService{

	/**
	 * 新增office_salary_sort
	 * @param officeSalarySort
	 * @return
	 */
	public OfficeSalarySort save(OfficeSalarySort officeSalarySort);

	/**
	 * 根据ids数组删除office_salary_sort数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_salary_sort
	 * @param officeSalarySort
	 * @return
	 */
	public Integer update(OfficeSalarySort officeSalarySort);

	/**
	 * 根据id获取office_salary_sort
	 * @param id
	 * @return
	 */
	public OfficeSalarySort getOfficeSalarySortById(String id);

	/**
	 * 根据ids数组查询office_salary_sortmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSalarySort> getOfficeSalarySortMapByIds(String[] ids);

	/**
	 * 获取office_salary_sort列表
	 * @return
	 */
	public List<OfficeSalarySort> getOfficeSalarySortList();

	/**
	 * 分页获取office_salary_sort列表
	 * @param page
	 * @return
	 */
	public List<OfficeSalarySort> getOfficeSalarySortPage(Pagination page);

	/**
	 * 根据UnitId获取office_salary_sort列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSalarySort> getOfficeSalarySortByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_salary_sort
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSalarySort> getOfficeSalarySortByUnitIdPage(String unitId, Pagination page);
	
	public OfficeSalarySort getOfficeSalarySortByImportId(String importId);
	
}