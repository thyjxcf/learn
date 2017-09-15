package net.zdsoft.office.dailyoffice.service;import java.util.*;import net.zdsoft.keel.util.Pagination;import net.zdsoft.office.dailyoffice.entity.OfficeLog;/** * office_log  * @author  *  */public interface OfficeLogService {	/**	 * 新增office_log	 * @param officeLog	 * @return	 */	public OfficeLog save(OfficeLog officeLog);	/**	 * 根据unitId userId获取office_log数据	 * @param unitId userId	 * @return	 */	public List<OfficeLog> getOfficeList(String unitId,String userId,String modId,String code);	/**	 * 根据ids数组删除office_log数据	 * @param ids	 * @return	 */	public Integer delete(String[] ids);		/**	 * 更新office_log	 * @param officeLog	 * @return	 */	public Integer update(OfficeLog officeLog);		/**	 * 根据id获取office_log	 * @param id	 * @return	 */	public OfficeLog getOfficeLogById(String id);		/**	 * 根据ids数组查询office_logmap	 * @param ids	 * @return	 */	public Map<String, OfficeLog> getOfficeLogMapByIds(String[] ids);		/**	 * 获取office_log列表	 * @return	 */	public List<OfficeLog> getOfficeLogList();		/**	 * 分页获取office_log列表	 * @param page	 * @return	 */	public List<OfficeLog> getOfficeLogPage(Pagination page);	

	/**
	 * 根据UnitId获取office_log列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLog> getOfficeLogByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_log
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLog> getOfficeLogByUnitIdPage(String unitId, Pagination page);}