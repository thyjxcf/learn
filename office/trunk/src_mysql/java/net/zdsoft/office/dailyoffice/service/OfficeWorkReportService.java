package net.zdsoft.office.dailyoffice.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
/**
 * office_work_report
 * @author 
 * 
 */
public interface OfficeWorkReportService{

	/**
	 * 新增office_work_report
	 * @param officeWorkReport
	 * @return
	 */
	public OfficeWorkReport save(OfficeWorkReport officeWorkReport);
	/**
	 * 新增office_work_report带附件
	 * @param officeWorkReport
	 * @return
	 */
	public void save(OfficeWorkReport officeWorkReport,UploadFile file);

	/**
	 * 根据ids数组删除office_work_report数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_report
	 * @param officeWorkReport
	 * @return
	 */
	public Integer update(OfficeWorkReport officeWorkReport);

	/**
	 * 根据id获取office_work_report
	 * @param id
	 * @return
	 */
	public OfficeWorkReport getOfficeWorkReportById(String id);

	/**
	 * 根据ids数组查询office_work_reportmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkReport> getOfficeWorkReportMapByIds(String[] ids);

	/**
	 * 获取office_work_report列表
	 * @return
	 */
	public List<OfficeWorkReport> getOfficeWorkReportList();

	/**
	 * 分页获取office_work_report列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReport> getOfficeWorkReportPage(Pagination page);

	/**
	 * 根据UnitId获取office_work_report列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_work_report
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据UnitId和其他条件进行查询
	 * @param unit
	 * @param page
	 * @param content
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPageContent(String unit,Pagination page,String userId,String content,String beginTime,String endTime,String reportType,String state);
	/**
	 * 根据UnitId和汇报人等条件查询
	 * @param unit
	 * @param page
	 * @param content
	 * @param beginTime
	 * @param endTime
	 * @param reportType
	 * @param createUserName
	 * @return
	 */
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPageContentCreateUserName(String userId,Pagination page,String content,String beginTime,String endTime,String reportType,String createUserName);
	
	/**
	 * 根据条件搜索记录
	 * @param receiveUserId
	 * @param userId
	 * @param createUserName
	 * @param content
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReport> getOfficeWorkReportList(String receiveUserId, String userId, String createUserName, String content, Pagination page);
	/**
	 * 微办公列表
	 * @param receiveUserId
	 * @param userId
	 * @param createUserName
	 * @param content
	 * @param type
	 * @param createTime
	 * @param state
	 * @param page
	 * @return
	 * 2016年7月27日
	 */
	public List<OfficeWorkReport> getOfficeWorkReportList(String receiveUserId, String userId, String createUserName, String content,String type,String createTime, Pagination page);
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	public OfficeWorkReport getOfficeWorkReportDetailsById(String id);
	/**
	 * 我可以查看的（收到的所有）
	 * @param receiveUserId
	 * @param type
	 * @param page
	 * @return
	 * 2016年7月26日
	 */
	public List<OfficeWorkReport> getOfficeWorkReportListICanRead(String receiveUserId,String type, Pagination page);
	/**
	 * 更新readUserId
	 * @param readUserId
	 * @return
	 */
	public Integer updateReadUserId(String readUserId,String id);
}