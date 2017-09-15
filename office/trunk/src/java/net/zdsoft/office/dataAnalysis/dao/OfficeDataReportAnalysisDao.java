package net.zdsoft.office.dataAnalysis.dao;

import java.util.*;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportAnalysis;
import net.zdsoft.keel.util.Pagination;

/**
 * office_data_report_analysis 
 * @author 
 * 
 */
public interface OfficeDataReportAnalysisDao {
	/**
	 * 新增office_data_report_analysis
	 * @param officeDataReportAnalysis
	 * @return"
	 */
	public OfficeDataReportAnalysis save(OfficeDataReportAnalysis officeDataReportAnalysis);
	
	/**
	 * 根据ids数组删除office_data_report_analysis
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public Integer deleteByUnitId(String unitId);
	
	/**
	 * 更新office_data_report_analysis
	 * @param officeDataReportAnalysis
	 * @return
	 */
	public Integer update(OfficeDataReportAnalysis officeDataReportAnalysis);
	
	/**
	 * 根据id获取office_data_report_analysis;
	 * @param id);
	 * @return
	 */
	public OfficeDataReportAnalysis getOfficeDataReportAnalysisById(String id);
		
	/**
	 * 根据ids数组查询office_data_report_analysismap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDataReportAnalysis> getOfficeDataReportAnalysisMapByIds(String[] ids);
	/**
	 * 根据单位id查信息 
	 * @param unitId
	 * @return
	 */
	public OfficeDataReportAnalysis getOfficeDataReportAnalysisByUnitId(String unitId);

	/**
	 * 根据analysis查信息
	 * @param analysis
	 * @return
	 */
	public OfficeDataReportAnalysis getOfficeDataReportAnalysis(OfficeDataReportAnalysis analysis);
	
	/**
	 * 获取office_data_report_analysis列表
	 * @return
	 */
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisList();
		
	/**
	 * 分页获取office_data_report_analysis列表
	 * @param page
	 * @return
	 */
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisPage(Pagination page);
	

	/**
	 * 根据unitId获取office_data_report_analysis列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_data_report_analysis获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisByUnitIdPage(String unitId, Pagination page);


	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisList(String[] unitIds, String year , String timeFrame);

	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisByYearTimeFrame(String year , String timeFrame);
}
