package net.zdsoft.office.dailyoffice.dao;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeLabInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_lab_info
 * @author 
 * 
 */
public interface OfficeLabInfoDao{

	/**
	 * 新增office_lab_info
	 * @param officeLabInfo
	 * @return
	 */
	public OfficeLabInfo save(OfficeLabInfo officeLabInfo);

	/**
	 * 根据ids数组删除office_lab_info
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
	 * 根据unitId获取office_lab_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_lab_info获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdPage(String unitId, Pagination page);
	
	public Map<String, OfficeLabInfo> getOfficeLabInfoMapByConditions(String unitId, String searchLabMode, String[] labSetIds);
}