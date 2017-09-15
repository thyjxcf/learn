package net.zdsoft.office.dailyoffice.dao;


import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportTl;
/**
 * office_work_report_tl
 * @author 
 * 
 */
public interface OfficeWorkReportTlDao{

	/**
	 * 新增office_work_report_tl
	 * @param officeWorkReportTl
	 * @return
	 */
	public OfficeWorkReportTl save(OfficeWorkReportTl officeWorkReportTl);

	/**
	 * 根据ids数组删除office_work_report_tl
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_report_tl
	 * @param officeWorkReportTl
	 * @return
	 */
	public Integer update(OfficeWorkReportTl officeWorkReportTl);

	/**
	 * 根据id获取office_work_report_tl
	 * @param id
	 * @return
	 */
	public OfficeWorkReportTl getOfficeWorkReportTlById(String id);

	/**
	 * 根据ids数组查询office_work_report_tlmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkReportTl> getOfficeWorkReportTlMapByIds(String[] ids);

	/**
	 * 获取office_work_report_tl列表
	 * @return
	 */
	public List<OfficeWorkReportTl> getOfficeWorkReportTlList();

	/**
	 * 分页获取office_work_report_tl列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReportTl> getOfficeWorkReportTlPage(Pagination page);

	/**
	 * 根据unitId获取office_work_report_tl列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_work_report_tl获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdPage(String unitId, Pagination page);
	/**
	 * 取得周次最大值
	 * @return
	 */
	public String findMaxWeek(String userId,String semesters,String unitId,String states);
	/**
	 * 根据UnitId和其他条件进行查询
	 * @param unit
	 * @param page
	 * @param content
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<OfficeWorkReportTl> getOfficeWorkReportByUnitIdPageContent(String unit,Pagination page,String userId,String acadyear,String semester,String week,String contents,String states);
	/**
	 * 根据UnitId和汇报人等条件查询
	 * @param page
	 * @param content
	 * @param createUserName
	 * @return
	 */
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdPageContentCreateUserName(Pagination page,String acadyear,String semester,String week,String contents,String createUserName);
	/**
	 * 根据UnitId和汇报人等判断周报是否存在
	 * @param unit
	 * @param page
	 * @param content
	 * @param beginTime
	 * @param endTime
	 * @param reportType
	 * @param createUserName
	 * @return
	 */
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitId(String unitId,String userId,String acadyear,String semester,String week);
}