package net.zdsoft.office.dataAnalysis.dao.impl;

import java.sql.*;
import java.util.*;

import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStrmanager;
import net.zdsoft.office.dataAnalysis.dao.OfficeDataReportStrmanagerDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import org.apache.commons.lang3.StringUtils;

/**
 * office_data_report_strmanager 
 * @author 
 * 
 */
public class OfficeDataReportStrmanagerDaoImpl extends BaseDao<OfficeDataReportStrmanager> implements OfficeDataReportStrmanagerDao{
	@Override
	public OfficeDataReportStrmanager setField(ResultSet rs) throws SQLException{
		OfficeDataReportStrmanager officeDataReportStrmanager = new OfficeDataReportStrmanager();
		officeDataReportStrmanager.setId(rs.getString("id"));
		officeDataReportStrmanager.setUnitId(rs.getString("unit_id"));
		officeDataReportStrmanager.setTeacherId(rs.getString("teacher_id"));
		officeDataReportStrmanager.setStreetIds(rs.getString("street_ids"));
		officeDataReportStrmanager.setCreationTime(rs.getTimestamp("creation_time"));
		officeDataReportStrmanager.setModifyTime(rs.getTimestamp("modify_time"));
		return officeDataReportStrmanager;
	}
	
	public OfficeDataReportStrmanager save(OfficeDataReportStrmanager officeDataReportStrmanager){
		String sql = "insert into office_data_report_strmanager(id, unit_id, teacher_id, street_ids, creation_time, modify_time) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDataReportStrmanager.getId())){
			officeDataReportStrmanager.setId(createId());
		}
		Object[] args = new Object[]{
			officeDataReportStrmanager.getId(), officeDataReportStrmanager.getUnitId(),
			officeDataReportStrmanager.getTeacherId(), officeDataReportStrmanager.getStreetIds(), 
			officeDataReportStrmanager.getCreationTime(), officeDataReportStrmanager.getModifyTime()
		};
		update(sql, args);
		return officeDataReportStrmanager;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_data_report_strmanager where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeDataReportStrmanager officeDataReportStrmanager){
		String sql = "update office_data_report_strmanager set unit_id = ?, teacher_id = ?, street_ids = ?, creation_time = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			officeDataReportStrmanager.getUnitId(), officeDataReportStrmanager.getTeacherId(),
			officeDataReportStrmanager.getStreetIds(), officeDataReportStrmanager.getCreationTime(), 
			officeDataReportStrmanager.getModifyTime(), officeDataReportStrmanager.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDataReportStrmanager getOfficeDataReportStrmanagerById(String id){
		String sql = "select * from office_data_report_strmanager where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	public List<OfficeDataReportStrmanager>  getOfficeDataReportStrmanagerByTeacherId(String teacherId){
		String sql = "select * from office_data_report_strmanager where teacher_id = ?";
		return query(sql, new Object[]{teacherId }, new MultiRow());
	}
	@Override
	public Map<String, OfficeDataReportStrmanager> getOfficeDataReportStrmanagerMapByIds(String[] ids){
		String sql = "select * from office_data_report_strmanager where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerList(){
		String sql = "select * from office_data_report_strmanager";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerPage(Pagination page){
		String sql = "select * from office_data_report_strmanager";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerByUnitIdPage(String unitId, Pagination page) {
		String sql = "select * from office_data_report_strmanager where unit_id = ?";
		if(page == null){
			return query(sql, new Object[]{unitId}, new MultiRow());
		}else{
			return query(sql, new Object[]{unitId}, new MultiRow(),page);

		}

	}
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerByUnitIdTeaId(String unitId, String teaId) {
		String sql = "select * from office_data_report_strmanager where unit_id = ? and teacher_id = ?";
		return query(sql, new Object[]{unitId ,teaId}, new MultiRow());


	}
	public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerByUnitIdStreetId(String unitId, String streetId) {
		String sql = "select * from office_data_report_strmanager where unit_id = ? and street_ids like ?";
		return query(sql, new Object[]{unitId ,"%"+streetId+"%"}, new MultiRow());


	}

}


