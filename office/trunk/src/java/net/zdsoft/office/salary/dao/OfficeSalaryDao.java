package net.zdsoft.office.salary.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.salary.entity.OfficeSalary;
/**
 * office_salary
 * @author 
 * 
 */
public interface OfficeSalaryDao{

	/**
	 * 新增office_salary
	 * @param officeSalary
	 * @return
	 */
	public OfficeSalary save(OfficeSalary officeSalary);

	/**
	 * 根据ids数组删除office_salary
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
	 * 根据unitId获取office_salary列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_salary获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 批量插入
	 * @param 
	 */
	public void batchInsert(List<OfficeSalary> officeSalarys);
	
	/**
	 * 根据单位id和项次id查询
	 * @param unitId
	 * @param importId
	 * @param page
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdId(String unitId, String importId,Pagination page);
	
	/**
	 * 根据单位和项次id和身份证进行查询
	 * @param unitId
	 * @param importId
	 * @param cardnumber
	 * @return
	 */
	public List<OfficeSalary> getOfficeSalaryByUnitIdAndCardnumber(String unitId,String importId,String userId,String cardNumber,Pagination page);
	
	public List<OfficeSalary> getOfficeSalaryListByImportId(String unitId,String[] importId);
}