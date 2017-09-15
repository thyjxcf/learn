package net.zdsoft.office.dailyoffice.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeTimeSet;
/**
 * office_time_set
 * @author 
 * 
 */
public interface OfficeTimeSetDao{

	/**
	 * 新增office_time_set
	 * @param officeTimeSet
	 * @return
	 */
	public OfficeTimeSet save(OfficeTimeSet officeTimeSet);

	/**
	 * 根据ids数组删除office_time_set
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_time_set
	 * @param officeTimeSet
	 * @return
	 */
	public Integer update(OfficeTimeSet officeTimeSet);

	/**
	 * 根据id获取office_time_set
	 * @param id
	 * @return
	 */
	public OfficeTimeSet getOfficeTimeSetById(String id);

	/**
	 * 根据ids数组查询office_time_setmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTimeSet> getOfficeTimeSetMapByIds(String[] ids);

	/**
	 * 获取office_time_set列表
	 * @return
	 */
	public List<OfficeTimeSet> getOfficeTimeSetList();

	/**
	 * 分页获取office_time_set列表
	 * @param page
	 * @return
	 */
	public List<OfficeTimeSet> getOfficeTimeSetPage(Pagination page);

	/**
	 * 根据unitId获取office_time_set列表
	 * @param unitId
	 * @return
	 */
	public OfficeTimeSet getOfficeTimeSetByUnitId(String unitId);

	/**
	 * 根据unitId分页office_time_set获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeTimeSet> getOfficeTimeSetByUnitIdPage(String unitId, Pagination page);
}