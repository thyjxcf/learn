package net.zdsoft.office.dailyoffice.service;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeCarSubsidy;
import net.zdsoft.keel.util.Pagination;
/**
 * office_car_subsidy
 * @author 
 * 
 */
public interface OfficeCarSubsidyService{

	/**
	 * 新增office_car_subsidy
	 * @param officeCarSubsidy
	 * @return
	 */
	public OfficeCarSubsidy save(OfficeCarSubsidy officeCarSubsidy);

	/**
	 * 根据ids数组删除office_car_subsidy数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_car_subsidy
	 * @param officeCarSubsidy
	 * @return
	 */
	public Integer update(OfficeCarSubsidy officeCarSubsidy);

	/**
	 * 根据id获取office_car_subsidy
	 * @param id
	 * @return
	 */
	public OfficeCarSubsidy getOfficeCarSubsidyById(String id);

	/**
	 * 根据ids数组查询office_car_subsidymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCarSubsidy> getOfficeCarSubsidyMapByIds(String[] ids);

	/**
	 * 获取office_car_subsidy列表
	 * @return
	 */
	public List<OfficeCarSubsidy> getOfficeCarSubsidyList();

	/**
	 * 分页获取office_car_subsidy列表
	 * @param page
	 * @return
	 */
	public List<OfficeCarSubsidy> getOfficeCarSubsidyPage(Pagination page);

	public List<OfficeCarSubsidy> getOfficeCarSubsidyList(String unitId);

	public void save(String unitId, String[] subsidyIds, String[] scopeNames,
			String[] subsidys);
}