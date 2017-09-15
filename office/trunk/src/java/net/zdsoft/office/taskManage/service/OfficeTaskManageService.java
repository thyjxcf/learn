package net.zdsoft.office.taskManage.service;

import java.util.*;
import net.zdsoft.office.taskManage.entity.OfficeTaskManage;
import net.zdsoft.keel.util.Pagination;
/**
 * office_task_manage
 * @author 
 * 
 */
public interface OfficeTaskManageService{

	/**
	 * 新增office_task_manage
	 * @param officeTaskManage
	 * @return
	 */
	public OfficeTaskManage save(OfficeTaskManage officeTaskManage);

	/**
	 * 根据ids数组删除office_task_manage数据
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
	 * 根据UnitId获取office_task_manage列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_task_manage
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 按条件查询获取任务列表
	 * @param unitId
	 * @param dealUserId
	 * @param states
	 * @param queryBeginDate
	 * @param queryEndDate
	 * @param page
	 * @return
	 */
	public List<OfficeTaskManage> getOfficeTaskManageListByCondition(String unitId, String dealUserId, String[] states, String queryBeginDate, String queryEndDate, Pagination page);
}