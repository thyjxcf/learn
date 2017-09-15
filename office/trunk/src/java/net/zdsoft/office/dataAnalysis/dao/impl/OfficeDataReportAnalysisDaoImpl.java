package net.zdsoft.office.dataAnalysis.dao.impl;

import java.sql.*;
import java.util.*;

import bsh.StringUtil;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportAnalysis;
import net.zdsoft.office.dataAnalysis.dao.OfficeDataReportAnalysisDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;

/**
 * office_data_report_analysis 
 * @author 
 * 
 */
public class OfficeDataReportAnalysisDaoImpl extends BaseDao<OfficeDataReportAnalysis> implements OfficeDataReportAnalysisDao{
	@Override
	public OfficeDataReportAnalysis setField(ResultSet rs) throws SQLException{
		OfficeDataReportAnalysis officeDataReportAnalysis = new OfficeDataReportAnalysis();
		officeDataReportAnalysis.setId(rs.getString("id"));
		officeDataReportAnalysis.setUnitId(rs.getString("unit_id"));
		officeDataReportAnalysis.setUserId(rs.getString("user_id"));
		officeDataReportAnalysis.setTeacherId(rs.getString("teacher_id"));
		officeDataReportAnalysis.setFilename(rs.getString("filename"));
		officeDataReportAnalysis.setFilesize(rs.getInt("filesize"));
		officeDataReportAnalysis.setDirId(rs.getString("dir_id"));
		officeDataReportAnalysis.setFilePath(rs.getString("file_path"));
		officeDataReportAnalysis.setCreationTime(rs.getTimestamp("creation_time"));
		officeDataReportAnalysis.setModifyTime(rs.getTimestamp("modify_time"));
		officeDataReportAnalysis.setExtName(rs.getString("ext_name"));
		officeDataReportAnalysis.setYear(rs.getString("year"));
		officeDataReportAnalysis.setTimeFrame(rs.getString("time_frame"));
		return officeDataReportAnalysis;
	}
	
	public OfficeDataReportAnalysis save(OfficeDataReportAnalysis officeDataReportAnalysis){
		String sql = "insert into office_data_report_analysis(id, unit_id, user_id, teacher_id, filename, filesize, dir_id, file_path, creation_time, modify_time, ext_name ,year , time_frame) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDataReportAnalysis.getId())){
			officeDataReportAnalysis.setId(createId());
		}
		Object[] args = new Object[]{
			officeDataReportAnalysis.getId(), officeDataReportAnalysis.getUnitId(), 
			officeDataReportAnalysis.getUserId(), officeDataReportAnalysis.getTeacherId(), 
			officeDataReportAnalysis.getFilename(), officeDataReportAnalysis.getFilesize(), 
			officeDataReportAnalysis.getDirId(), officeDataReportAnalysis.getFilePath(), 
			officeDataReportAnalysis.getCreationTime(), officeDataReportAnalysis.getModifyTime(), 
			officeDataReportAnalysis.getExtName(),officeDataReportAnalysis.getYear(),officeDataReportAnalysis.getTimeFrame()
		};
		update(sql, args);
		return officeDataReportAnalysis;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_data_report_analysis where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer deleteByUnitId(String unitId) {
		String sql = "delete from office_data_report_analysis where unit_id  = ?";
		return update(sql, unitId);
	}

	@Override
	public Integer update(OfficeDataReportAnalysis officeDataReportAnalysis){
		String sql = "update office_data_report_analysis set unit_id = ?, user_id = ?, teacher_id = ?, filename = ?, filesize = ?, dir_id = ?, file_path = ?, creation_time = ?, modify_time = ?, ext_name = ? ,year = ? ,time_frame = ?  where id = ?";
		Object[] args = new Object[]{
			officeDataReportAnalysis.getUnitId(), officeDataReportAnalysis.getUserId(), 
			officeDataReportAnalysis.getTeacherId(), officeDataReportAnalysis.getFilename(), 
			officeDataReportAnalysis.getFilesize(), officeDataReportAnalysis.getDirId(), 
			officeDataReportAnalysis.getFilePath(), officeDataReportAnalysis.getCreationTime(), 
			officeDataReportAnalysis.getModifyTime(), officeDataReportAnalysis.getExtName(), officeDataReportAnalysis.getYear(),
				officeDataReportAnalysis.getTimeFrame(),
			officeDataReportAnalysis.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDataReportAnalysis getOfficeDataReportAnalysisById(String id){
		String sql = "select * from office_data_report_analysis where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	public OfficeDataReportAnalysis getOfficeDataReportAnalysisByUnitId(String unitId){
		String sql = "select * from office_data_report_analysis where unit_id = ? and rownum <= 1 order by creation_time desc";
		return query(sql, new Object[]{unitId }, new SingleRow());
	}

	@Override
	public OfficeDataReportAnalysis getOfficeDataReportAnalysis(OfficeDataReportAnalysis analysis) {
		String sql = "select * from office_data_report_analysis where unit_id = ?  and year = ? and time_frame = ? and rownum <= 1 order by creation_time desc";

		return query(sql, new Object[]{analysis.getUnitId(),analysis.getYear(),analysis.getTimeFrame() }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDataReportAnalysis> getOfficeDataReportAnalysisMapByIds(String[] ids){
		String sql = "select * from office_data_report_analysis where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisList(){
		String sql = "select * from office_data_report_analysis";
		return query(sql, new MultiRow());
	}
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisList(String[] unitIds, String year , String timeFrame){
		String sql = "select * from office_data_report_analysis where  year = ? and time_frame = ? and unit_id  in ";


		return queryForInSQL(sql, new Object[]{year , timeFrame}, unitIds,  new MultiRow());
	}
	@Override
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisPage(Pagination page){
		String sql = "select * from office_data_report_analysis";
		return query(sql, new MultiRow(), page);
	}

	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisByYearTimeFrame(String year , String timeFrame){
		String sql = "select * from office_data_report_analysis where  year = ? and time_frame = ? ";
		return query(sql,new Object[]{year ,timeFrame}, new MultiRow());
	}

	@Override
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisByUnitIdList(String unitId){
		String sql = "select * from office_data_report_analysis where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDataReportAnalysis> getOfficeDataReportAnalysisByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_data_report_analysis where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}
