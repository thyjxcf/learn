package net.zdsoft.office.dataAnalysis.dao.impl;

import java.sql.*;
import java.util.*;

import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStreet;
import net.zdsoft.office.dataAnalysis.dao.OfficeDataReportStreetDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import org.apache.commons.lang3.StringUtils;

/**
 * office_data_report_street 
 * @author 
 * 
 */
public class OfficeDataReportStreetDaoImpl extends BaseDao<OfficeDataReportStreet> implements OfficeDataReportStreetDao{
	@Override
	public OfficeDataReportStreet setField(ResultSet rs) throws SQLException{
		OfficeDataReportStreet officeDataReportStreet = new OfficeDataReportStreet();
		officeDataReportStreet.setId(rs.getString("id"));
		officeDataReportStreet.setUnitId(rs.getString("unit_id"));
		officeDataReportStreet.setStreetName(rs.getString("street_name"));
		officeDataReportStreet.setSchoolIds(rs.getString("school_ids"));
		officeDataReportStreet.setCreationTime(rs.getTimestamp("creation_time"));
		officeDataReportStreet.setModifyTime(rs.getTimestamp("modify_time"));
		return officeDataReportStreet;
	}
	
	public OfficeDataReportStreet save(OfficeDataReportStreet officeDataReportStreet){
		String sql = "insert into office_data_report_street(id, unit_id, street_name, school_ids, creation_time, modify_time) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDataReportStreet.getId())){
			officeDataReportStreet.setId(createId());
		}
		Object[] args = new Object[]{
			officeDataReportStreet.getId(), officeDataReportStreet.getUnitId(),
			officeDataReportStreet.getStreetName(), officeDataReportStreet.getSchoolIds(), 
			officeDataReportStreet.getCreationTime(), officeDataReportStreet.getModifyTime()
		};
		update(sql, args);
		return officeDataReportStreet;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_data_report_street where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeDataReportStreet officeDataReportStreet){
		String sql = "update office_data_report_street set unit_id = ?, street_name = ?, school_ids = ?, creation_time = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			officeDataReportStreet.getUnitId(), officeDataReportStreet.getStreetName(),
			officeDataReportStreet.getSchoolIds(), officeDataReportStreet.getCreationTime(), 
			officeDataReportStreet.getModifyTime(), officeDataReportStreet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDataReportStreet getOfficeDataReportStreetById(String id){
		String sql = "select * from office_data_report_street where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDataReportStreet> getOfficeDataReportStreetMapByIds(String[] ids){
		String sql = "select * from office_data_report_street where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	public List<OfficeDataReportStreet> getOfficeDataReportStreetListByIds(String[] ids){
		String sql = "select * from office_data_report_street where id in";
		return queryForInSQL(sql ,null,ids, new MultiRow());
	}
	@Override
	public List<OfficeDataReportStreet> getOfficeDataReportStreetList(){
		String sql = "select * from office_data_report_street";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDataReportStreet> getOfficeDataReportStreetPage(Pagination page){
		String sql = "select * from office_data_report_street";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDataReportStreet> getOfficeDataReportStreetByUnitIdPage(String unitId, Pagination page) {
		String sql = "select * from office_data_report_street where unit_id = ? ";
		if(page != null){
			return query(sql, new Object[]{unitId }, new MultiRow(), page);
		}else{
			return query(sql ,new Object[]{unitId }, new MultiRow());
		}

	}
	public List<OfficeDataReportStreet> getOfficeDataReportStreetByUnitIdStreetName(String unitId, String streetName) {
		String sql = "select * from office_data_report_street where unit_id = ?  and street_name = ? ";

		return query(sql ,new Object[]{unitId ,streetName }, new MultiRow());

	}

	@Override
	public Map<String, OfficeDataReportStreet> getOfficeDataStreetMapByUnitid(String unitId) {
		String sql = "select * from office_data_report_street where unit_id = ? ";

		return queryForMap(sql, new Object[]{unitId}, new MapRowMapper<String, OfficeDataReportStreet>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}

			@Override
			public OfficeDataReportStreet mapRowValue(ResultSet rs, int rowNum) throws SQLException {
				OfficeDataReportStreet street = setField(rs);
				return street;
			}
		});
	}

}


