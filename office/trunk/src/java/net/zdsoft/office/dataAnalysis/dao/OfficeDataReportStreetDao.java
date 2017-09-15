package net.zdsoft.office.dataAnalysis.dao;

import java.util.*;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStreet;
import net.zdsoft.keel.util.Pagination;

/**
 * office_data_report_street 
 * @author 
 * 
 */
public interface OfficeDataReportStreetDao {
	/**
	 * 新增office_data_report_street
	 * @param officeDataReportStreet
	 * @return"
	 */
	public OfficeDataReportStreet save(OfficeDataReportStreet officeDataReportStreet);
	
	/**
	 * 根据ids数组删除office_data_report_street
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_data_report_street
	 * @param officeDataReportStreet
	 * @return
	 */
	public Integer update(OfficeDataReportStreet officeDataReportStreet);
	
	/**
	 * 根据id获取office_data_report_street;
	 * @param id);
	 * @return
	 */
	public OfficeDataReportStreet getOfficeDataReportStreetById(String id);
		
	/**
	 * 根据ids数组查询office_data_report_streetmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDataReportStreet> getOfficeDataReportStreetMapByIds(String[] ids);
	
	/**
	 * 获取office_data_report_street列表
	 * @return
	 */
	public List<OfficeDataReportStreet> getOfficeDataReportStreetList();
		
	/**
	 * 分页获取office_data_report_street列表
	 * @param page
	 * @return
	 */
	public List<OfficeDataReportStreet> getOfficeDataReportStreetPage(Pagination page);

	/**
	 * 根据 unitid获取 list数据
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDataReportStreet> getOfficeDataReportStreetByUnitIdPage(String unitId ,Pagination page);


	public Map<String,OfficeDataReportStreet> getOfficeDataStreetMapByUnitid(String unitId);

	public List<OfficeDataReportStreet> getOfficeDataReportStreetListByIds(String[] ids);

	public List<OfficeDataReportStreet> getOfficeDataReportStreetByUnitIdStreetName(String unitId, String streetName);
}
