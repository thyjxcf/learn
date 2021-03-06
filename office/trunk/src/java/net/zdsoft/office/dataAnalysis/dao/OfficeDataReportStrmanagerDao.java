package net.zdsoft.office.dataAnalysis.dao;

import java.util.*;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStrmanager;
import net.zdsoft.keel.util.Pagination;

/**
 * office_data_report_strmanager 
 * @author 
 * 
 */
public interface OfficeDataReportStrmanagerDao {
	/**
	 * 新增office_data_report_strmanager
	 * @param officeDataReportStrmanager
	 * @return"
	 */
	public OfficeDataReportStrmanager save(OfficeDataReportStrmanager officeDataReportStrmanager);
	
	/**
	 * 根据ids数组删除office_data_report_strmanager
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_data_report_strmanager
	 * @param officeDataReportStrmanager
	 * @return
	 */
	public Integer update(OfficeDataReportStrmanager officeDataReportStrmanager);
	
	/**
	 * 根据id获取office_data_report_strmanager;
	 * @param id);
	 * @return
	 */
	public OfficeDataReportStrmanager getOfficeDataReportStrmanagerById(String id);
		
	/**
	 * 根据ids数组查询office_data_report_strmanagermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDataReportStrmanager> getOfficeDataReportStrmanagerMapByIds(String[] ids);
	
	/**
	 * 获取office_data_report_strmanager列表
	 * @return
	 */
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerList();
		
	/**
	 * 分页获取office_data_report_strmanager列表
	 * @param page
	 * @return
	 */
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerPage(Pagination page);


	/**
	 * 分页获取office_data_report_strmanager列表
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerByUnitIdPage(String unitId ,Pagination page);


	public List<OfficeDataReportStrmanager>  getOfficeDataReportStrmanagerByTeacherId(String teacherId);

	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerByUnitIdTeaId(String unitId, String teaId);

	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerByUnitIdStreetId(String unitId, String streetId);

}
