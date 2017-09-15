package net.zdsoft.office.health.dao;

import java.util.*;
import net.zdsoft.office.health.entity.OfficeHealthCount;
import net.zdsoft.keel.util.Pagination;

/**
 * office_health_count 
 * @author 
 * 
 */
public interface OfficeHealthCountDao {
	/**
	 * 新增office_health_count
	 * @param officeHealthCount
	 * @return"
	 */
	public OfficeHealthCount save(OfficeHealthCount officeHealthCount);
	
	/**
	 * 根据ids数组删除office_health_count
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_health_count
	 * @param officeHealthCount
	 * @return
	 */
	public Integer update(OfficeHealthCount officeHealthCount);
	
	/**
	 * 根据id获取office_health_count;
	 * @param id);
	 * @return
	 */
	public OfficeHealthCount getOfficeHealthCountById(String id);
	/**
	 * 根据studentId获取 List office_health_count;
	 * @param studentId);
	 * @return
	 */
	public List<OfficeHealthCount> getOfficeHealthCountByStudentId(String studentId,String nowDateStr,String beforeDateStr);
	/**
	 * 根据studentIds   日期类型是天 获取 List office_health_count;
	 * @param studentIds);
	 * @return
	 */
	public List<OfficeHealthCount> getClassAllStuByStudentIds(String[] studentIds,String queryDate);
		
	/**
	 * 根据ids数组查询office_health_countmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeHealthCount> getOfficeHealthCountMapByIds(String[] ids);
	
	/**
	 * 获取office_health_count列表
	 * @return
	 */
	public List<OfficeHealthCount> getOfficeHealthCountList();
		
	/**
	 * 分页获取office_health_count列表
	 * @param page
	 * @return
	 */
	public List<OfficeHealthCount> getOfficeHealthCountPage(Pagination page);
	
}
