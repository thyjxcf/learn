package net.zdsoft.office.dailyoffice.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityNumber;
/**
 * office_utility_number
 * @author 
 * 
 */
public interface OfficeUtilityNumberService{

	/**
	 * 新增office_utility_number
	 * @param officeUtilityNumber
	 * @return
	 */
	public void save(OfficeUtilityNumber officeUtilityNumber);
	
	/**
	 * 批量新增
	 * @param officeUtilityNumbers
	 */
	public void addOfficeUtilityNumbers(List<OfficeUtilityNumber> officeUtilityNumbers);

	/**
	 * 根据ids数组删除office_utility_number数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_utility_number
	 * @param officeUtilityNumber
	 * @return
	 */
	public Integer update(OfficeUtilityNumber officeUtilityNumber);

	/**
	 * 根据id获取office_utility_number
	 * @param id
	 * @return
	 */
	public OfficeUtilityNumber getOfficeUtilityNumberById(String id);

	/**
	 * 根据ids数组查询office_utility_numbermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeUtilityNumber> getOfficeUtilityNumberMapByIds(String[] ids);

	/**
	 * 获取office_utility_number列表
	 * @return
	 */
	public List<OfficeUtilityNumber> getOfficeUtilityNumberList();

	/**
	 * 分页获取office_utility_number列表
	 * @param page
	 * @return
	 */
	public List<OfficeUtilityNumber> getOfficeUtilityNumberPage(Pagination page);

	/**
	 * 根据UnitId获取office_utility_number列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeUtilityNumber> getOfficeUtilityNumberByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_utility_number
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeUtilityNumber> getOfficeUtilityNumberByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据申请单号ids获取具体的申请信息ids
	 * @param applyNumberIds
	 * @return
	 */
	public String[] getUtilityApplyIds(String[] applyNumberIds);
}