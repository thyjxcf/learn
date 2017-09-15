package net.zdsoft.office.dailyoffice.service;import java.util.*;import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportEx;import net.zdsoft.keel.util.Pagination;/** * office_work_report_ex  * @author  *  */public interface OfficeWorkReportExService {	/**	 * 新增office_work_report_ex	 * @param officeWorkReportEx	 * @return	 */	public OfficeWorkReportEx save(OfficeWorkReportEx officeWorkReportEx);		/**	 * 根据ids数组删除office_work_report_ex数据	 * @param ids	 * @return	 */	public Integer delete(String[] ids);		/**	 * 更新office_work_report_ex	 * @param officeWorkReportEx	 * @return	 */	public Integer update(OfficeWorkReportEx officeWorkReportEx);		/**	 * 根据id获取office_work_report_ex	 * @param id	 * @return	 */	public OfficeWorkReportEx getOfficeWorkReportExById(String id);		/**	 * 根据ids数组查询office_work_report_exmap	 * @param ids	 * @return	 */	public Map<String, OfficeWorkReportEx> getOfficeWorkReportExMapByIds(String[] ids);		/**	 * 获取office_work_report_ex列表	 * @return	 */	public List<OfficeWorkReportEx> getOfficeWorkReportExList();		/**	 * 分页获取office_work_report_ex列表	 * @param page	 * @return	 */	public List<OfficeWorkReportEx> getOfficeWorkReportExPage(Pagination page);	

	/**
	 * 根据UnitId获取office_work_report_ex列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_work_report_ex
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdPage(String unitId, Pagination page);	/**	 * 根据reportId获取office_work_report_ex列表	 * @param unitId	 * @return	 */	public List<OfficeWorkReportEx> getOfficeWorkReportExByrepotIdList(String reportId);}