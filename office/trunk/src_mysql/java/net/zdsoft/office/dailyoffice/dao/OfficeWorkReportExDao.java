package net.zdsoft.office.dailyoffice.dao;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportEx;
import net.zdsoft.keel.util.Pagination;

/**
 * office_work_report_ex 
 * @author 
 * 
 */
public interface OfficeWorkReportExDao {
	/**
	 * 新增office_work_report_ex
	 * @param officeWorkReportEx
	 * @return"
	 */
	public OfficeWorkReportEx save(OfficeWorkReportEx officeWorkReportEx);
	
	/**
	 * 根据ids数组删除office_work_report_ex
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_work_report_ex
	 * @param officeWorkReportEx
	 * @return
	 */
	public Integer update(OfficeWorkReportEx officeWorkReportEx);
	
	/**
	 * 根据id获取office_work_report_ex;
	 * @param id);
	 * @return
	 */
	public OfficeWorkReportEx getOfficeWorkReportExById(String id);
		
	/**
	 * 根据ids数组查询office_work_report_exmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkReportEx> getOfficeWorkReportExMapByIds(String[] ids);
	
	/**
	 * 获取office_work_report_ex列表
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExList();
		
	/**
	 * 分页获取office_work_report_ex列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExPage(Pagination page);
	

	/**
	 * 根据unitId获取office_work_report_ex列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_work_report_ex获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据reportId获取office_work_report_ex列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExByrepotIdList(String reportId);
}
