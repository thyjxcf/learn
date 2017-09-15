package net.zdsoft.office.taskManage.dao;

import java.util.*;

import net.zdsoft.office.taskManage.entity.OfficeTaskManage;
import net.zdsoft.keel.util.Pagination;
/**
 * office_task_manage
 * @author 
 * 
 */
public interface OfficeTaskManageDao{

	/**
	 * 新增office_task_manage
	 * @param officeTaskManage
	 * @return
	 */
	public OfficeTaskManage save(OfficeTaskManage officeTaskManage);

	/**
	 * 根据ids数组删除office_task_manage
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_task_manage
	 * @param officeTaskManage
	 * @return
	 */
	public Integer update(OfficeTaskManage officeTaskManage);

	/**
	 * 根据id获取office_task_manage
	 * @param id
	 * @return
	 */
	public OfficeTaskManage getOfficeTaskManageById(String id);

	/**
	 * 根据ids数组查询office_task_managemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTaskManage> getOfficeTaskManageMapByIds(String[] ids);

	/**
	 * 获取office_task_manage列表
	 * @return
	 */
	public List<OfficeTaskManage> getOfficeTaskManageList();

	/**
	 * 分页获取office_task_manage列表
	 * @param page
	 * @return
	 */
	public List<OfficeTaskManage> getOfficeTaskManagePage(Pagination page);

	/**
	 * 根据unitId获取office_task_manage列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_task_manage获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeTaskManage> getOfficeTaskManageListByCondition(String unitId, String dealUserId, String[] states, String queryBeginDate, String queryEndDate, Pagination page);
}