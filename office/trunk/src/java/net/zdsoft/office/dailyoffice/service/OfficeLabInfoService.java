package net.zdsoft.office.dailyoffice.service;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeLabInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_lab_info
 * @author 
 * 
 */
public interface OfficeLabInfoService{

	/**
	 * 新增office_lab_info
	 * @param officeLabInfo
	 * @return
	 */
	public OfficeLabInfo save(OfficeLabInfo officeLabInfo);

	/**
	 * 根据ids数组删除office_lab_info数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_lab_info
	 * @param officeLabInfo
	 * @return
	 */
	public Integer update(OfficeLabInfo officeLabInfo);

	/**
	 * 根据id获取office_lab_info
	 * @param id
	 * @return
	 */
	public OfficeLabInfo getOfficeLabInfoById(String id);

	/**
	 * 根据ids数组查询office_lab_infomap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLabInfo> getOfficeLabInfoMapByIds(String[] ids);

	/**
	 * 获取office_lab_info列表
	 * @return
	 */
	public List<OfficeLabInfo> getOfficeLabInfoList();

	/**
	 * 分页获取office_lab_info列表
	 * @param page
	 * @return
	 */
	public List<OfficeLabInfo> getOfficeLabInfoPage(Pagination page);

	/**
	 * 根据UnitId获取office_lab_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_lab_info
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据条件搜索
	 * @param unitId
	 * @param labSetIds
	 * @param searchLabMode
	 * @return
	 */
	public Map<String, OfficeLabInfo> getOfficeLabInfoMapByConditions(String unitId, String searchLabMode, String[] labSetIds);
}