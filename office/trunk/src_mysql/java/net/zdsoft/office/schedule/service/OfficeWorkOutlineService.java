package net.zdsoft.office.schedule.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.entity.OfficeWorkOutline;
/**
 * office_work_outline
 * @author 
 * 
 */
public interface OfficeWorkOutlineService{

	/**
	 * 新增office_work_outline
	 * @param officeWorkOutline
	 * @return
	 */
	public OfficeWorkOutline save(OfficeWorkOutline officeWorkOutline);

	/**
	 * 根据ids数组删除office_work_outline数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_outline
	 * @param officeWorkOutline
	 * @return
	 */
	public Integer update(OfficeWorkOutline officeWorkOutline);

	/**
	 * 根据id获取office_work_outline
	 * @param id
	 * @return
	 */
	public OfficeWorkOutline getOfficeWorkOutlineById(String id);

	/**
	 * 根据ids数组查询office_work_outlinemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkOutline> getOfficeWorkOutlineMapByIds(String[] ids);

	/**
	 * 获取office_work_outline列表
	 * @return
	 */
	public List<OfficeWorkOutline> getOfficeWorkOutlineList();

	/**
	 * 分页获取office_work_outline列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkOutline> getOfficeWorkOutlinePage(Pagination page);

	/**
	 * 根据UnitId获取office_work_outline列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkOutline> getOfficeWorkOutlineByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_work_outline
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWorkOutline> getOfficeWorkOutlineByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据参数查询列表 一周
	 * @param unitId
	 * @param deptId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String,List<OfficeWorkOutline>> getOfficeWorkOutlinesBySearchParams(List<Date> weekDate,String unitId,String deptId,Date startTime,Date endTime);
	
	/**
	 * 检查周重点工作新增时的事假冲突
	 * @param unitId
	 * @param id  对象id
	 * @param deptId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean isExistConflict(String unitId,String deptId,String id,Date startTime,Date endTime);
	
	/**
	 * 查询某一天的记录
	 * @param unitId
	 * @param deptId
	 * @param date
	 * @return
	 */
	public List<OfficeWorkOutline> getOfficeWorkOutlineByDay(String unitId,String deptId,Date date);
	
	/**
	 * 根据period分段查询一天中的记录
	 * @param unitId
	 * @param deptId
	 * @param date
	 * @param period
	 * @return
	 */
	public List<OfficeWorkOutline> getOfficeWorkOutlineByPeriodOfDay(String unitId,String deptId,Date date,int period);
	
	/**
	 * 获取一个月的记录
	 * @param unitId
	 * @param deptId
	 * @param dates
	 * @return
	 */
	public Map<String,List<OfficeWorkOutline>> getOfficeWorkOutlinesByMonth(String unitId,String deptId,Date firstDay,Date lastDay, List<Date> dates);
	
}