package net.zdsoft.office.dailyoffice.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeSigntimeSet;
/**
 * office_signtime_set
 * @author 
 * 
 */
public interface OfficeSigntimeSetDao{

	/**
	 * 新增office_signtime_set
	 * @param officeSigntimeSet
	 * @return
	 */
	public OfficeSigntimeSet save(OfficeSigntimeSet officeSigntimeSet);

	/**
	 * 根据ids数组删除office_signtime_set
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_signtime_set
	 * @param officeSigntimeSet
	 * @return
	 */
	public Integer update(OfficeSigntimeSet officeSigntimeSet);

	/**
	 * 根据id获取office_signtime_set
	 * @param id
	 * @return
	 */
	public OfficeSigntimeSet getOfficeSigntimeSetById(String id);

	/**
	 * 根据ids数组查询office_signtime_setmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSigntimeSet> getOfficeSigntimeSetMapByIds(String[] ids);

	/**
	 * 获取office_signtime_set列表
	 * @return
	 */
	public List<OfficeSigntimeSet> getOfficeSigntimeSetList();

	/**
	 * 分页获取office_signtime_set列表
	 * @param page
	 * @return
	 */
	public List<OfficeSigntimeSet> getOfficeSigntimeSetPage(Pagination page);

	/**
	 * 根据unitId获取office_signtime_set列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSigntimeSet> getOfficeSigntimeSetByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_signtime_set获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSigntimeSet> getOfficeSigntimeSetByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据time判断超时
	 * @param unitId
	 * @param page
	 * @return
	 */
	public OfficeSigntimeSet getOfficeSigntimeSetByUnitIdPage(String unitId, String time);
}