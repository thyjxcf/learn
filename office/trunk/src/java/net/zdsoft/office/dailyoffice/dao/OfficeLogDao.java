package net.zdsoft.office.dailyoffice.dao;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeLog;

/**
 * office_log 
 * @author 
 * 
 */
public interface OfficeLogDao {
	/**
	 * 新增office_log
	 * @param officeLog
	 * @return"
	 */
	public OfficeLog save(OfficeLog officeLog);
	/**
	 * 根据unitId userId获取office_log数据
	 * @param unitId userId
	 * @return
	 */
	public List<OfficeLog> getOfficeList(String unitId,String userId,String modId,String code);
	/**
	 * 根据ids数组删除office_log
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_log
	 * @param officeLog
	 * @return
	 */
	public Integer update(OfficeLog officeLog);
	
	/**
	 * 根据id获取office_log;
	 * @param id);
	 * @return
	 */
	public OfficeLog getOfficeLogById(String id);
		
	/**
	 * 根据ids数组查询office_logmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLog> getOfficeLogMapByIds(String[] ids);
	
	/**
	 * 获取office_log列表
	 * @return
	 */
	public List<OfficeLog> getOfficeLogList();
		
	/**
	 * 分页获取office_log列表
	 * @param page
	 * @return
	 */
	public List<OfficeLog> getOfficeLogPage(Pagination page);
	

	/**
	 * 根据unitId获取office_log列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLog> getOfficeLogByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_log获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLog> getOfficeLogByUnitIdPage(String unitId, Pagination page);
}
