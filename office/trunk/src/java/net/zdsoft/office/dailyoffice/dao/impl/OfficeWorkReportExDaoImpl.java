package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportEx;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkReportExDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringUtils;

/**
 * office_work_report_ex 
 * @author 
 * 
 */
public class OfficeWorkReportExDaoImpl extends BaseDao<OfficeWorkReportEx> implements OfficeWorkReportExDao{
	@Override
	public OfficeWorkReportEx setField(ResultSet rs) throws SQLException{
		OfficeWorkReportEx officeWorkReportEx = new OfficeWorkReportEx();
		officeWorkReportEx.setId(rs.getString("id"));
		officeWorkReportEx.setUnitId(rs.getString("unit_id"));
		officeWorkReportEx.setReportId(rs.getString("report_id"));
		officeWorkReportEx.setUserId(rs.getString("user_id"));
		officeWorkReportEx.setCreateTime(rs.getTimestamp("create_time"));
		officeWorkReportEx.setContent(rs.getString("content"));
		return officeWorkReportEx;
	}
	
	@Override
	public OfficeWorkReportEx save(OfficeWorkReportEx officeWorkReportEx){
		String sql = "insert into office_work_report_ex(id, unit_id, report_id, user_id, create_time, content) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeWorkReportEx.getId())){
			officeWorkReportEx.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkReportEx.getId(), officeWorkReportEx.getUnitId(), 
			officeWorkReportEx.getReportId(), officeWorkReportEx.getUserId(), 
			officeWorkReportEx.getCreateTime(), officeWorkReportEx.getContent()
		};
		update(sql, args);
		return officeWorkReportEx;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_report_ex where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public void delete(String reportId) {
		String sql="delete from office_work_report_ex where report_id=?";
		update(sql, new String[]{reportId});
	}

	@Override
	public Integer update(OfficeWorkReportEx officeWorkReportEx){
		String sql = "update office_work_report_ex set unit_id = ?, report_id = ?, user_id = ?, create_time = ?, content = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkReportEx.getUnitId(), officeWorkReportEx.getReportId(), 
			officeWorkReportEx.getUserId(), officeWorkReportEx.getCreateTime(), 
			officeWorkReportEx.getContent(), officeWorkReportEx.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWorkReportEx getOfficeWorkReportExById(String id){
		String sql = "select * from office_work_report_ex where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkReportEx> getOfficeWorkReportExMapByIds(String[] ids){
		String sql = "select * from office_work_report_ex where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExList(){
		String sql = "select * from office_work_report_ex";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExPage(Pagination page){
		String sql = "select * from office_work_report_ex";
		return query(sql, new MultiRow(), page);
	}
	

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdList(String unitId){
		String sql = "select * from office_work_report_ex where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_work_report_ex where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExByrepotIdList(
			String reportId) {
		String sql = "select * from office_work_report_ex where report_id = ? order by create_time desc";
		return query(sql, new Object[]{reportId }, new MultiRow());
	}
}


	